from audio import load_audio_file,get_volume,get_wordspersecond
import flask
import time
from flask import Flask, flash, request, redirect, url_for, jsonify


app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/test', methods=['GET'])
def test():
    return "<h1>Distant Reading Archive</h1><p>This site is a prototype API for distant reading of science fiction novels.</p>"

@app.route('/audio', methods=['POST'])
def audio_handler():
    print("audio handler")
    
    filename = './download/'+str(time.time())+".mp4"
    rec = request.files.get('recording')
    rec.save(filename)
    print(request.form['script'])
    data = load_audio_file(filename, reset = False)
    average_volume = get_volume(data)
    words_per_sec = get_wordspersecond(data)
    return jsonify(
        volume = average_volume,
        word = words_per_sec
    )

app.run()