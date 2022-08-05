from unicodedata import decimal
import torch
import cv2
import numpy as np
from fer_pytorch import fer
import mediapipe as mp


def get_facial_gesture_score(path):
    f=fer.FER()
    f.get_pretrained_model("resnet34")
    frame_num = 50
    video = cv2.VideoCapture(path)
    duration = video.get(cv2.CAP_PROP_POS_MSEC)
    total_frame = video.get(cv2.CAP_PROP_FRAME_COUNT)
    frame_ratio = int(total_frame/frame_num)

    #extract frames
    s = []
    frame_count=0
    while True:
        ret, frame = video.read()

        if ret:
            if(frame_count%frame_ratio==0):
                s.append(frame)
            frame_count=frame_count+1
        else:
            break
    video.release()
    cv2.destroyAllWindows()


    emotion_catogory = ['neutral','happiness','surprise','sadness','anger','disgust','fear']
    i=0
    mpHands = mp.solutions.hands
    hands = mpHands.Hands(static_image_mode=False,
                        max_num_hands=2,
                        min_detection_confidence=0.5,
                        min_tracking_confidence=0.5)
    emotion_score={}
    for emotion in emotion_catogory:
        emotion_score[emotion]=0
    valid_frame=0
    hand_landmark=[]
    for frame in s:
        result = f.predict_image(frame)
        if(result!=[]):
            for emotion in emotion_catogory:
                emotion_score[emotion]=emotion_score[emotion]+result[0]['emotions'][emotion]
            valid_frame=valid_frame+1
        imgRGB = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        hand_result = hands.process(imgRGB)
        
        if hand_result.multi_hand_landmarks:
            for handLms in hand_result.multi_hand_landmarks:
                for id, lm in enumerate(handLms.landmark):
                    hand_landmark.append([lm.x,lm.y])
    listr=[]
    for emotion in emotion_catogory:
        listr.append(emotion_score[emotion]/valid_frame)
    std = np.std(listr)
    hand_landmark=np.array(hand_landmark)
    hand_std = np.std(hand_landmark[:,0])+np.std(hand_landmark[:,1])
    facial_score = 60+(0.34-std)*100
    hand_score = 0
    if hand_std<0.02:
        hand_score=70
    elif hand_score<0.04:
        hand_score=80
    elif hand_score<0.06:
        hand_score=90
    else:
        hand_score=100

    facial_score=round(facial_score,3)
    return round((hand_score+facial_score)/2,3),round(hand_score,3),round(facial_score,3)
