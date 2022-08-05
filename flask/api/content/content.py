"""Content-Topic relevance rating API."""
import json
import os
import numpy as np
from nltk.tokenize import word_tokenize
# TODO: nltk.download('stopwords') run this line once after NLTK installation
from nltk.corpus import stopwords
stopwords = set(stopwords.words('english'))
rmstopword = lambda w: w not in stopwords
word2vec = dict()


def load_embedding_file(embedding):
    """
    Load embedding into memory.
    """
    if embedding == "glove":
        embed_path = os.path.join("embeddings", "glove.6B.50d.txt")
        print(embed_path)
        if not os.path.exists(embed_path):
            print("ERROR! Embedding file not found. First run ./download.sh")
            exit(1)
        with open(embed_path,'r') as embed_file:
            for line in embed_file:
                values = line.split()
                word = values[0]
                vector = np.asarray(values[1:],'float32')
                word2vec[word] = vector


def encode(sentence):
    """
    Returns a vector representation of the sentence using the embedding.
    """
    vectors = list()
    for word in sentence:
        try:
            vectors.append(word2vec[word])
        except:
            continue
    return np.mean(vectors, 0)


def relevance_score(topic, text, embedding="glove"):
    """
    Returns a score in (-1, 1) indicating the relevance between text and topic.
    """
    topic = word_tokenize(topic.lower()) 
    text = word_tokenize(text.lower())
    topic = list(filter(rmstopword, topic))
    text = list(filter(rmstopword, text))
    load_embedding_file(embedding)
    topic = encode(topic)
    text = encode(text)
    cosine = np.dot(topic, text) / np.linalg.norm(topic) / np.linalg.norm(text)
    return cosine


def load_sample_text(filepath):
    """
    Returns topic and text contained in the file.
    """
    with open(filepath, 'r') as text_file:
        data = json.load(text_file)
    return [data["topic"], data["text"]]


def script_memorization_score(transcript, script):
    """
    Returns a 0-10 score of the Jaccard index between the transcribed script
    provided in `transcript` and the actual prepared script in `script`.
    """
    transcript = set(word_tokenize(transcript.lower()))
    script = set(word_tokenize(script.lower()))
    words_union = transcript | script
    words_intersect = transcript & script
    return round(len(words_intersect) / (len(words_union) + 0.001), 1)


def speech_fluency_score(transcript):
    """
    Returns a rating of the how many stopwords (useless mumbling) are used
    during a presentation.
    """
    ratings = ["excellent", "good", "plain", "needs work"]
    transcript = word_tokenize(transcript.lower())
    transcript_cleaned = list(filter(rmstopword, transcript))
    stopwords_count = len(transcript) - len(transcript_cleaned)
    ratio = stopwords_count / (len(transcript) + 1.0)
    for i in range(len(ratings)):
        if ratio <= (i+1) * 1.0 / len(ratings):
            return ratings[i]
    return ratings[-1]
