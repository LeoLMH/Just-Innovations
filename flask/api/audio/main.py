import audio
import os

test_audio = os.path.join("sample_audio", "ted_talk.wav")  #"shot.mp3"#
data = audio.load_audio_file(test_audio, reset = False)

print("The audio's average volume is")
print(audio.get_volume(data))

print("The audio's average speed (words oer second) is")
print(audio.get_wordspersecond(data))

print("The audio's transcribed text is")
# print(audio.audio_to_text(test_audio))