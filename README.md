# 441-team-project

(I simply copy and paste the template here, you can modified it.)

## Getting Started

The link to all 3rd-party tools, libraries, SDKs, APIs that may be used in our project are listed below:

+ **Speech to Text API**: [Google Cloud Speech-to-Text](https://cloud.google.com/speech-to-text)
+ **Audio Analysis**: [Librosa](https://librosa.org/doc/latest/index.html)
+ **Tone Analysis**: [torchaudio](https://pytorch.org/audio/stable/index.html)
+ **Pretrained Multi-task Cascaded Convolutional Networks**: [fer-pytorch](https://pypi.org/project/fer-pytorch/)
+ **Hand Motion Track**: [mediapipe](https://google.github.io/mediapipe/)
+ **Neural Network Framework**: [Pytorch](https://pytorch.org/)
+ **Computer Vision Toolkit**: [OpenCV](https://pypi.org/project/opencv-python/)
+ ...

## Model and Engine

+ **Story Map**:
![alt text](https://github.com/LeoLMH/441-team-project/blob/main/storymap.png?raw=true)

+ **System Architecture**:
![alt text](https://github.com/LeoLMH/441-team-project/blob/main/architecture.png?raw=true)

**Front End**:
Firsly user will send his/her scripts, rehearsal recording and topic through our front end.

**Back End - Main Handler**:
The main handler maintains connection and listens to front end's message. It fetches all the inputs other engines require and send to them.

**Back End - Vision Handler**:
The vision handler extracs frames from the video and sends them to engines. MTCNN performs facial expression classification. It firstly detects the bounding box for face and outputs a 7-dimensional vector which represenst the probability of neutral, happiness, surprise, sadness, anger, disgust and fear. 

On the other hand, the mediapipe library provides a 3-stage pretrained neural network which is used to detect bounding box for gestures, analyze gestures and perform landmark regression. By tracking the landmark displacements, it outputs a score to reflect gesture usages.

**Back End - Voice Handler**:
The voice handler relies on Speech-To-Text results from Google Cloud. It sends a request along with a piece of audio recording to Google Cloud and gets transcripts. Stop word counts are directly obtained from those transcripts and sent to the feedback handler. Furthermore, the ground true script provided by users can be used to compare with the rehearsals. The 
percentage of correctly recited words is sent to feedback handler.

The voice handler uses librosa library to get the start and stop time of each word. In this way, the engine calculates a fluent score for every 5 consecutive words. Given that time period, the maximum and minimum waveform magnitudes are checked for each word. The average volume of two consecutive words is passed to feedback handler.

Last but not least, the voice handler uses torchaudio model to learn to analyze positive and negative emotions from audio. The model can also compare sentiment of the audio with topics selected by users. The analysis results are sent to feedback handler.

**Back End - Feedback Handler**:
The feedback handler receives various outputs from voice handler and vision handler. It sends some real-time feedbacks such as fluent score and volume score back to front-end. It also synchronizes other complex analysis and outputs some instructions to front-end.


## APIs and Controller

**Request Parameters**

| Key           | Location       | Type   | Description                              |
| ------------- | -------------- | ------ | ---------------------------------------- |
| `username`    | Session Cookie | String | Current user                             |
| `script_path` |                | String | Location of script file uploaded by user |
| `audio_path`  |                | String | Location of audio file recorded by user  |

**Response Codes**

| Code              | Description        |
| ----------------- | ------------------ |
| `200 OK`          | Success            |
| `400 Bad Request` | Invalid parameters |

**Returns**

*If no user is logged in or no posts created by user*

| Key             | Location | Type                      | Description                                  |
| --------------- | -------- | ------------------------- | -------------------------------------------- |
| `popular_songs` | JSON     | List of Spotify Track IDs | Top 25 songs on Spotify in the United States |

*For logged-in users with 1 or more posts created*

| Key                         | Location | Type                      | Description                                                  |
| --------------------------- | -------- | ------------------------- | ------------------------------------------------------------ |
| `attribute_recommendations` | JSON     | List of Spotify Track IDs | Attribute-based recommendations (random genres)              |
| `genre_recommendations`     | JSON     | List of Spotify Track IDs | Attribute and genre-based recommendations based on user's favorite genres |
| `artist_recommendations`    | JSON     | List of Spotify Track ID  | Attribute and artist-based recommendations based on user's favorite artists |
| `attribute_error`           | JSON     | Dictionary                | contains the average error % for each attribute between recommendation and the user's attribute vector. |

**Example**



### Third-Party SDKs

## View UI/UX

This section is left blank for now.

## Team Roster

The contribution division will be added later.

| Name         | Contribution |
| ------------ | ------------ |
| Chenshu Zhu  |              |
| Minhao Li    |              |
| Junqi Chen   |              |
| Yuqing Wang  |              |
| Yangqin Yan  |              |
| Jiaming Kang |              |

