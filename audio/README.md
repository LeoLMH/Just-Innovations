# Audio Processing API

A `main.py` is provided for demo purposes. It must be run from the `/audio` directory.

## Functions

```
def load_audio_file(filepath, reset=False):
    """
    Loads and preprocesses an audio file from `filepath`.
    returns data = {
        "waveform": (y, sr),
        "onsets": onsets,
        "energy": rms
    }  
    """
```

```
def get_volume(data, tic=0, toc=None):
    """
    Returns the average volume for audio provided in `data` between
    time `tic` and `toc` (in seconds). If not specified, `toc` = `tic` + 1.
    """
```

```
def get_wordspersecond(data, tic=0, toc=None):
    """
    Returns the speed-of-speech in words/second for audio provided in `data`
    between time `tic` and `toc` (in seconds). If not specified, `toc` = end.
    """
```