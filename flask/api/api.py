from concurrent.futures import thread
import json

#from grpc import server
from audio.audio import load_audio_file,get_volume,get_wordspersecond, audio_to_text
from content.content import speech_fluency_score, script_memorization_score, relevance_score
from vision import *
import flask
import time
from flask import Flask, flash, request, redirect, url_for, jsonify
import ffmpy

import os

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/test', methods=['GET'])
def test():
    return "<h1>Distant Reading Archive</h1><p>This site is a prototype API for distant reading of science fiction novels.</p>"


@app.route('/score/', methods=['POST'])
def score():

    # save recording
    print("receive a request")
    rec = request.files['recording']
    if not os.path.exists('./download'):
        os.mkdir('./download')
    filename = './download/' + str(int(time.time())) + '.mp4'
    if not os.path.exists('./'+user_name) :
        os.mkdir('./'+user_name)
    #rec.save(filename)
    #filename: file name of presentation recording .mp4
    #requests.form['presentation_title']: string
    #requests.form['presentation_topic']: string
    #requests.form['script']: string
    #requests.form['user_name]: string

    visual_score,gesture_score,facial_score=get_facial_gesture_score(filename) 
    print("visual finish")
    #mp4, presentation title, username

    # change any media format to wav
    
    #fmt = rec.filename.split(".")[-1]
    '''
    fmt = 'wav'
    #filename = './download/' + str(int(time.time())) + "." + fmt
    filename='./download/demo_cropped_cut.wav'
    #rec.save(filename)
    if fmt != "wav":
        source_file = filename
        sink_file = './download/' + str(int(time.time())) + ".wav"
        ff = ffmpy.FFmpeg(
            inputs={source_file: None},
            outputs={sink_file: None})
        ff.run()
        filename = sink_file
    # load wav audio, get script, get topic, get speech as text
    data = load_audio_file(filename, reset=False)
    script = request.form['script']
    text_speech = audio_to_text(filename)
    topic = request.form['topic']
    
    print("@@@")
    # get average volume, word_per_sec, text_speech
    average_volume = get_volume(data)
    words_per_sec = get_wordspersecond(data)
    print(words_per_sec)
    '''
    face_suggestion=""
    gesture_suggestion=""
    
    if(facial_score<60):
        face_suggestion="Your facial expression is too neutral. Try to be more energetic and use your passion to ignite the audience."
    elif(facial_score<70):
        face_suggestion="Sometimes you need to change your facial expression more to attract the audience."
    elif(facial_score<80):
        face_suggestion="Overall speaking your facial expressions are good. But there is still some improvement. You can turn your face around or try to smile during the presentation."
    else:
        face_suggestion="You did a great job. Try to keep this feeling in the real presentation!"

    if(gesture_score<60):
        gesture_suggestion="Your hands were not moving. Try to use your body language to get the audience involved"
    elif(gesture_score<70):
        gesture_suggestion="You had several attempts on using gestures. Try to use more!"
    else:
        gesture_suggestion="Your body language is good. Try to keep this feeling in the real presentation!"

    speech_score = 
    volume_score = 
    pace_score = 
    overall_score = (speech_score+visual_score)/2

    suggestion = 
    
    #save to local
    user_name = request.form['user_name']
    pre_title = request.form['presentation_title']
    record_name = './'+user_name+'/'+pre_title+'.json'
    d = {}


    cur_path = os.path.dirname(os.path.realpath(__file__))
    save_path = os.path.join(cur_path, record_name)
    if not os.path.exists(os.path.dirname(save_path)):
        os.mkdir(os.path.dirname(save_path))
    print(save_path)

    
    d["overall_score"] = str(overall_score)
    d["speech_score"] = 
    d["volume_score"] = str(volume_score)
    d["pace_score"] = str(pace_score)
    d["visual_score"] = str(visual_score)
    d["gesture_score"] = str(gesture_score)
    d["facial_score"] = str(facial_score)
    d["suggestion"] = str(suggestion)
    d["gesture_sug"] = gesture_suggestion
    d["face_sug"]= face_suggestion
    d["vol_sug"] = 
    d["pace_sug"] = 
    d["flue_sug"] = 
    d["memo_sug"] = 
    d["flue_score"] = 
    d["memo_score"] = 
    with open(save_path, 'w') as f:
        json.dump(d,f)
    return jsonify(
        overall_score = str(speech_score),
        speech_score = ,
        volume_score = str(volume_score),
        pace_score = str(pace_score),
        visual_score = str(visual_score),
        gesture_score = str(gesture_score),
        facial_score = str(facial_score),
        suggestion = str(suggestion),
        gesture_sug = gesture_suggestion,
        face_sug = face_suggestion,
        vol_sug = ,
        pace_sug = 
        flue_sug = 
        memo_sug =
        flue_score = 
        memo_score = 

    )
    # return jsonify(
    #     overall_score = str(speech_score),
    #     speech_score = str(speech_score),
    #     volume_score = str(volume_score),
    #     pace_score = str(pace_score),
    #     visual_score = str(visual_score),
    #     gesture_score = str(gesture_score),
    #     facial_score = str(facial_score),
    #     suggestion = str(suggestion),
    # )

@app.route('/recent/', methods=['POST'])
def retrieve():
    user_name = request.form['username']
    print(user_name)
    recent_json_list = os.listdir('./'+user_name)
    recent_json=None
    for f in recent_json_list:
        print(f)
        with open('./'+user_name+'/'+f,'r') as file:
            j = json.load(file)
            recent_json=j
    return jsonify(recent_json)

app.run()
#app.run(host='0.0.0.0',port='8000')
#server(app,host='12.34.56.78',port=8080,thread=1)