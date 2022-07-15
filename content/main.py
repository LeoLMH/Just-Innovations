import os
import content


text_file_path = os.path.join("sample_text", "gunlaw.json")
topic, text = content.load_sample_text(text_file_path)
print(content.relevance_score(topic, text, "glove"))