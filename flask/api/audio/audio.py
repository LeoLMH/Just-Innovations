"""Audio Processing API."""
import os
import pickle
import librosa
import numpy as np
import requests


# https://www.assemblyai.com/
API_KEY = "86b72a9847804598bf224fc1c26d45b0"


def load_audio_file(filepath, reset=False):
    """
    Loads and preprocesses an audio file from `filepath`.
    returns data = {
        "waveform": (y, sr),
        "onsets": onsets,
        "energy": rms
    }  
    """
    basename = os.path.basename(filepath)
    filename, ext = os.path.splitext(basename)
    if not os.path.exists(".cache"):
        os.mkdir(".cache")
    datapath = os.path.join(".cache", filename + ".npz")

    # only process the audio file if it has not been processed before
    if not os.path.exists(datapath) or reset:
        y, sr = librosa.load(filepath)
        onsets = librosa.onset.onset_detect(y=y, sr=sr, backtrack=True)
        rms = librosa.feature.rms(y=y)[0]
        data = {
            "waveform": (y, sr),
            "onsets": onsets,
            "energy": rms
        }

        with open(datapath, "wb") as datafile:
            pickle.dump(data, datafile)
    else:
        with open(datapath, "rb") as datafile:
            data = pickle.load(datafile)
    return data


def get_volume(data, tic=0, toc=None):
    """
    Returns the average volume for audio provided in `data` between
    time `tic` and `toc` (in seconds). If not specified, `toc` = `tic` + 1.
    """
    y, sr = data["waveform"]
    xmax = sr * toc if toc else sr * (tic + 1)
    avg_energy = np.mean(data["energy"][sr * tic : xmax])
    return 100 - abs(int((avg_energy - 0.05) * 100))


def get_wordspersecond(data, tic=0, toc=None):
    """
    Returns the speed-of-speech in words/second for audio provided in `data`
    between time `tic` and `toc` (in seconds). If not specified, `toc` = end.
    """
    y, sr = data["waveform"]
    xmax = sr * toc if toc else len(y) - 1
    words = filter(lambda x: x >= sr * tic and x < xmax, data["onsets"])
    words_count = len(list(words))
    words_per_sec = words_count / (xmax / (sr + 0.0) - tic)
    return 100 - abs(int((words_per_sec - 5) * 10)) 


def volume_handler(filepath, reset=False):
    data = audio.load_audio_file(test_audio, reset = False)


def read_file_generator(filepath, chunk_size=5242880):
    """
    A helper function for audio_to_text().
    """
    with open(filepath, 'rb') as _file:
        while True:
            data = _file.read(chunk_size)
            if not data:
                break
            yield data


def audio_to_text(filepath):
    """
    Returns the transcribed text of the audio file specified in the filepath.
    Note that the audio file must be in .wav format.
    """
    headers = {
        'authorization': API_KEY
    }
    response = requests.post('https://api.assemblyai.com/v2/upload',
                            headers=headers,
                            data=read_file_generator(filepath))
    cloud_url = response.json()["upload_url"]
    endpoint = "https://api.assemblyai.com/v2/transcript"
    json = {
        "audio_url": cloud_url
    }
    headers = {
        "authorization": API_KEY,
        "content-type": "application/json"
    }
    response = requests.post(endpoint, json=json, headers=headers)
    transcript_id = response.json()['id']
    endpoint = f"https://api.assemblyai.com/v2/transcript/{transcript_id}"
    headers = {
        "authorization": API_KEY,
    }
    # use a while loop to wait for the api to return 
    while True:
        response = requests.get(endpoint, headers=headers)
        if response.json()['status'] == "completed":
            break
    return response.json()["text"]
