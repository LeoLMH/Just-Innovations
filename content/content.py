"""Content-Topic relevance rating API."""
import json
import os
import numpy as np
from nltk.tokenize import word_tokenize
# TODO: nltk.download('stopwords') run this line once after NLTK installation
from nltk.corpus import stopwords
stopwords = set(stopwords.words('english'))
word2vec = dict()


def load_embedding_file(embedding):
    """
    Load embedding into memory.
    """
    if embedding is "glove":
        embed_path = os.path.join("embeddings", "glove.6B.50d.txt")
        if not os.path.exists(embed_path):
            print("First run download.sh")
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
    rmstopword = lambda w: w not in stopwords
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
