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
    user_name = request.form['user_name']
    pre_title = request.form['presentation_title']

    # save recording
    print("receive a request")
    rec = request.files['recording']
    if not os.path.exists('./download'):
        os.mkdir('./download')
    filename = './download/' + str(int(time.time())) + '.mp4'
    if not os.path.exists('./'+user_name) :
        os.mkdir('./'+user_name)

    visual_score,gesture_score,facial_score = get_facial_gesture_score(filename)

    print("visual finish")
    # mp4, presentation title, username
    # change any media format to wav
    fmt = rec.filename.split(".")[-1]
    filename = './download/' + str(int(time.time())) + "." + fmt
    rec.save(filename)
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

    # get average volume_score, pace_score
    volume_score = get_volume(data)
    pace_score = get_wordspersecond(data)

    # get relevance score, fluency score and meorization score
    fluency_score = speech_fluency_score(text_speech)
    memo_score = script_memorization_score(text_speech, script)
    rel_score = relevance_score(topic, text_speech)

    # generate speech_score
    speech_score = (fluency_score + memo_score + volume_score + pace_score)/4

    overall_score = (speech_score+visual_score)/2

    # generate suggestions

    if facial_score < 60:
        face_suggestion = "Your facial expression is too neutral. Try to be more energetic and use your passion to ignite the audience."
    elif facial_score < 70:
        face_suggestion = "Sometimes you need to change your facial expression more to attract the audience."
    elif facial_score < 80:
        face_suggestion = "Overall speaking your facial expressions are good. But there is still some improvement. You can turn your face around or try to smile during the presentation."
    else:
        face_suggestion = "You did a great job. Try to keep this feeling in the real presentation!"

    if gesture_score < 60:
        gesture_suggestion = "Your hands were not moving. Try to use your body language to get the audience involved"
    elif gesture_score < 70:
        gesture_suggestion = "You had several attempts on using gestures. Try to use more!"
    else:
        gesture_suggestion = "Your body language is good. Try to keep this feeling in the real presentation!"

    if volume_score < 60:
        volume_suggestion = "Generally speaking, Your volume is either too high or too low. An improper volume will make the audience hard to understand you."
    elif volume_score < 70:
        volume_suggestion = "More control over your volume is preferred. Try to calm down and don't be nervous."
    elif volume_score < 80:
        volume_suggestion = "Your volume is generally good. But sometimes you may unconciously make your voice too high or too low."
    else:
        volume_suggestion = "Your volume is proper.Try to keep the speaking volume in your real presentation."

    if pace_score < 60:
        pace_suggestion = "You really need to practise more. You keep a improper pace."
    elif pace_score < 70:
        pace_suggestion = "Sometimes you are speaking fast while sometime you are speaking slow. This will confuse audience."
    elif pace_score < 80:
        pace_suggestion = "You are speaking too fast. Slow down the pace so that the audience can hear clearer."
    else:
        pace_suggestion = "You keep a good speed. This will contribute much to making yourself clear."

    if fluency_score < 60:
        flue_suggestion = "It seems that you haven't remember your script. Try to remember it before presentation practice."
    elif fluency_score < 70:
        flue_suggestion = "You are not totally familiar with your script. The number of stop words in your presentation is unignorable"
    elif fluency_score < 80:
        flue_suggestion = "Acceptable fluency. There are some stop words in your presentation."
    else:
        flue_suggestion = "Perfect, few stop words detected"

    if memo_score < 60:
        memo_suggestion = "Your speech is so different from the script that I suspect you have uploaded the wrong script."
    elif memo_score < 70:
        memo_suggestion = "Much difference between your speech and the script. Try to memorize the script better."
    elif memo_score < 80:
        memo_suggestion = "You have memorized most of your script. One step from perfect. Keep practising."
    else:
        memo_suggestion = "Your memorization is perfect. Don't panic in the real presentation. Good Luck"

    if overall_score < 60:
        suggestion = "This is not an ideal presentation. Few facial expressions and gestures. Improper pace and volume. Please practise more."
    elif overall_score < 70:
        suggestion = "This presentation needs improvement. You should add more gestures and facial expressions. Keeping a good pace and volume is also essential."
    elif overall_score < 80:
        suggestion = "One step from a good presentation. Maybe you need a little more control over volume or pace. Or you may need to get more familiar with your script."
    else:
        suggestion = "The overall presentation is good. You have used gestures during your presentation. Your script memorization is perfect. The facial expression is too simple."
    
    # save to local
    record_name = './'+user_name+'/'+pre_title+'.json'
    d = {}

    cur_path = os.path.dirname(os.path.realpath(__file__))
    save_path = os.path.join(cur_path, record_name)
    if not os.path.exists(os.path.dirname(save_path)):
        os.mkdir(os.path.dirname(save_path))
    print(save_path)
    
    d["overall_score"] = str(overall_score)
    d["speech_score"] = str(speech_score)

    d["volume_score"] = str(volume_score)
    d["pace_score"] = str(pace_score)
    d["visual_score"] = str(visual_score)
    d["gesture_score"] = str(gesture_score)
    d["facial_score"] = str(facial_score)
    d["suggestion"] = str(suggestion)

    d["gesture_sug"] = gesture_suggestion
    d["face_sug"]= face_suggestion
    d["vol_sug"] = volume_suggestion
    d["pace_sug"] = pace_suggestion
    d["flue_sug"] = flue_suggestion
    d["memo_sug"] = memo_suggestion
    d["flue_score"] = fluency_score
    d["memo_score"] = memo_score
    d["rel_score"] = rel_score

    with open(save_path, 'w') as f:
        json.dump(d,f)
    return jsonify(
        overall_score = str(speech_score),
        speech_score = str(speech_score),
        volume_score = str(volume_score),
        pace_score = str(pace_score),
        visual_score = str(visual_score),
        gesture_score = str(gesture_score),
        facial_score = str(facial_score),
        suggestion = str(suggestion),

        gesture_sug = gesture_suggestion,
        face_sug = face_suggestion,
        vol_sug = volume_suggestion,
        pace_sug = pace_suggestion,
        flue_sug = flue_suggestion,
        memo_sug = memo_suggestion,
        flue_score = str(fluency_score),
        memo_score = str(memo_score),
        rel_score = str(rel_score)


    )

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


app.run(host='0.0.0.0', port='8000')

