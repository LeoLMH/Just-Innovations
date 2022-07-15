"""Audio Processing API."""
import os
import pickle
import librosa
import numpy as np

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
    return avg_energy


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
    return words_per_sec

def volume_handler(filepath, reset=False):
    data = audio.load_audio_file(test_audio, reset = False)