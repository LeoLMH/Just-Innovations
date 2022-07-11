import audio
import os

test_audio = os.path.join("sample_audio", "ted_talk.mp3")  #"shot.mp3"#
data = audio.load_audio_file(test_audio, reset = False)
print(audio.get_volume(data))
print(audio.get_wordspersecond(data))