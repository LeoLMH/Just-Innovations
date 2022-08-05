from concurrent.futures import thread
import json

#from grpc import server
from audio.audio import load_audio_file,get_volume,get_wordspersecond, audio_to_text
from content.content import speech_fluency_score, script_memorization_score, relevance_score
#from vision import *
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

@app.route('/audio/', methods=['POST'])
def audio_handler():
    print("audio handler")
    '''
    rec = request.files['recording']
    fmt = rec.filename.split(".")[-1]

    filename = './download/' + str(int(time.time())) + "." + fmt
    rec.save(filename)

    source_file = filename
    sink_file = './download/' + str(int(time.time())) + ".wav"
    print(sink_file)
    ff = ffmpy.FFmpeg(
        inputs={source_file: None},
        outputs={sink_file: None})
    ff.run()

    print(request.form['script'])
    data = load_audio_file(sink_file, reset = False)
    average_volume = get_volume(data)
    print(average_volume)
    words_per_sec = get_wordspersecond(data)
    print(words_per_sec)
    return jsonify(
        volume = float(average_volume),
        word = float(words_per_sec)
    )
    '''
    return jsonify(
        volume_score = "999",
    )

@app.route('/score/', methods=['POST'])
def score():

    # save recording
    print("receive a request")
    rec = request.files['recording']
    if not os.path.exists('./download'):
        os.mkdir('./download')
    # filename = './download/' + str(int(time.time())) + '.mp4'
    # user_name = "user1"
    # pre_title = "pre1"
    # if not os.path.exists('./'+user_name) :
    #     os.mkdir('./'+user_name)
    # rec.save(filename)
    #filename: file name of presentation recording .mp4
    #requests.form['presentation_title']: string
    #requests.form['presentation_topic']: string
    #requests.form['script']: string
    #requests.form['user_name]: string

    #visual_score,gesture_score,facial_score=get_facial_gesture_score(filename)    
    #mp4, presentation title, username

    # change any media format to wav
    fmt = rec.filename.split(".")[-1]
    filename = './download/' + str(int(time.time())) + "." + fmt
    rec.save(filename)
    source_file = filename
    sink_file = './download/' + str(int(time.time())) + ".wav"
    ff = ffmpy.FFmpeg(
        inputs={source_file: None},
        outputs={sink_file: None})
    ff.run()

    # load wav audio, get script, get topic, get speech as text
    data = load_audio_file(sink_file, reset=False)
    script = request.form['script']
    text_speech = audio_to_text(sink_file)
    topic = request.form['topic']

    print("@@@")
    # get average volume, word_per_sec, text_speech
    average_volume = get_volume(data)
    words_per_sec = get_wordspersecond(data)
    print("@@@")
    # get relevance score, fluency score and meorization score
    fluency_score = speech_fluency_score(text_speech)
    memo_score = script_memorization_score(text_speech, script)
    rel_score = relevance_score(topic, text_speech)
    print("@@@")

    visual_score,gesture_score,facial_score = 60,70,80
    speech_score = 80
    volume_score = 90
    pace_score = 90
    overall_score = (speech_score+visual_score)/2
    suggestion = "The overall presentation good. This is a sample suggestion text"
    #save to local
    user_name = request.form['user_name']
    pre_title = request.form['presentation_title']
    record_name = './'+user_name+'/'+pre_title+'.json'
    d = {}
    d["overall_score"] = str(overall_score),
    d["speech_score"] = str(speech_score),
    d["volume_score"] = str(volume_score),
    d["pace_score"] = str(pace_score),
    d["visual_score"] = str(visual_score),
    d["gesture_score"] = str(gesture_score),
    d["facial_score"] = str(facial_score),
    d["suggestion"] = str(suggestion),
    with open(record_name, 'w') as f:
        json.dump(d,f)
    return jsonify(
        average_volume=str(average_volume),
        words_per_sec=str(words_per_sec),
        fluency_score=str(fluency_score),
        memo_score=str(memo_score),
        # relevance_score=str(rel_score)
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

@app.route('/recent/', methods=['GET'])
def retrieve():
    user_name = "user1"
    recent_json_list = os.listdir('./'+user_name)
    recent_list = []
    for f in recent_json_list:
        print(f)
        with open('./'+user_name+'/'+f,'r') as file:
            j = json.load(file)
            recent_list.append(j)
    return jsonify(recent_list)

@app.route('/image/', methods=['GET'])
def send_image():
    user_name = "user1"
    file_name = './user1/test.jpg'
    flask.send_file(file_name,mimetype='image/jpg')
    time.sleep(1)
    return 

#app.run()
app.run(host='0.0.0.0',port='8000')
#server(app,host='12.34.56.78',port=8080,thread=1)
