from concurrent.futures import thread
import json

from grpc import server

from audio import load_audio_file,get_volume,get_wordspersecond
import flask
import time
from flask import Flask, flash, request, redirect, url_for, jsonify

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

app.run(host='0.0.0.0',port='8000')
#server(app,host='12.34.56.78',port=8080,thread=1)
