import audio
import os

test_audio = os.path.join("sample_audio", "ted_talk.wav")  #"shot.mp3"#
#data = audio.load_audio_file(test_audio, reset = False)
#print(audio.get_volume(data))
#print(audio.get_wordspersecond(data))
audio.audio_to_text(test_audio)