import os
import content


text_file_path = os.path.join("sample_text", "gunlaw.json")
topic, text = content.load_sample_text(text_file_path)

print("The relevance score is")
print(content.relevance_score(topic, text, "glove"))

print("The fluency rating is")
print(content.speech_fluency_score(text))

print("The script memorization score is")
print(content.script_memorization_score(text, text))