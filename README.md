# 441-team-project

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
Firsly user will send a request which contains a script, rehearsal recording and topic through our front end.

**Back End - Main Handler**:
The main handler maintains connection and listens to front end's message. It fetches all the inputs other engines require and send to them.

**Back End - Vision Handler**:
The vision handler extracs frames from the video and sends them to engines. MTCNN performs facial expression classification. It firstly detects the bounding box for face and outputs a 7-dimensional vector which represenst the probability of neutral, happiness, surprise, sadness, anger, disgust and fear. 

On the other hand, the mediapipe library provides a 3-stage pretrained neural network which is used to detect bounding box for gestures, analyze gestures and perform landmark regression. By tracking the landmark displacements, it outputs a score to reflect gesture usages.

**Back End - Voice Handler**:
The voice handler relies on Speech-To-Text results from Google Cloud. It sends a request along with a piece of audio recording to Google Cloud and gets transcripts. Stop word counts are directly obtained from those transcripts and sent to the feedback handler. Furthermore, the ground true script provided by users can be used to compare with the rehearsals. The 
percentage of correctly recited words is sent to feedback handler.

The voice handler uses librosa library to verity the start and stop time of each word produced by Google API. In this way, the engine calculates a fluent score for every 5 consecutive words. Given that time period, the maximum and minimum waveform magnitudes are checked for each word. The average volume of two consecutive words is passed to feedback handler.

Last but not least, the voice handler uses torchaudio model to learn to analyze positive and negative emotions from audio. The model can also compare sentiment of the audio with topics selected by users. The analysis results are sent to feedback handler.

**Back End - Feedback Handler**:
The feedback handler receives various outputs from voice handler and vision handler. It sends some real-time feedbacks such as fluent score and volume score back to front-end. It also synchronizes other complex analysis and outputs some instructions to front-end.


## APIs and Controller

**Front-End Request Parameters**

| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `username`    |  String | Current user                             |
| `title` |  String | Presentation Title                       |
| `topic`     | String     | Presentation Topic  |
| `script`    | String     | Presentation Script |
| `recording`  | Media File | Rehearsal Recording  |

**Google Cloud Speech-to-text Request Parameters**

| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `uri`    |  String | Path to Audio File                            |
| `encoding` |  String | Type of Audio Encoding |
| `languageCode`  | String | Audio Language  |
| `sampleRateHertz`  | String | Sampling rate of audio  |

**Google Cloud Speech-to-text Responde Parameters**

| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `transcript`    |  String | Transcripts of Audio File                           |
| `confidence` |  Float | Confidence value (0-1)|
| `word`  | dict | Start Time and Stop Time of Each Word in Transcript  |

**Back-End Responde Parameters**
| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `overall_score` | Float  | Score from 0 to 100 which evaluates users' general presentation performance |
| `speech_score`  | String | An assessment from lists [excellent, good, plain, needs work] which evaluates users' overall speech performance |
| `volume_score`  | Float  | Score from 0 to 10 which evaluates users' speaking volume    |
| `pace_score`    | Float  | Score from 0 to 100 which evaluates users' presentation speed |
| `visual_score`  | Float  | Score from 0 to 100 which evaluates users' overall visual performance |
| `gesture_score` | Float  | Score from 0 to 10 which evaluates users' gesture usages     |
| `facial_score`  | Float  | Score from 0 to 10 which evaluates users' facial expressions |
| `flue_score`    | String | Score from 0 to 10 which evaluates the users' speech fluency |
| memo_score      | Float  | Score from 0 to 100 which evaluates users' level of memorization of the script |
| `suggestion`    | String | General suggestion generated from backend based on users' presentation materials |
| `gesture_sug`   | String | Suggestion on presenter's gesture                            |
| `face_sug`      | String | Suggestion on presenter's facial expression                  |
| `vol_sug`       | String | Suggestion on presenter's volume                             |
| `pace_sug`      | String | Suggestion on presenter's speaking speed                     |
| `flue_sug`      | String | Suggestion on presenter's speaking fluency                   |
| `memo_sug`      | String | Suggestion on presenter's level of memorization of the script. |

## View UI/UX

This section is left blank for now.

## Team Roster

The contribution division will be added later.

| Name         | Contribution                                                 |
| ------------ | ------------------------------------------------------------ |
| Chenshu Zhu  | Build presentation audio rating api. Build content-topic correlation rating api. Manage git repo. |
| Minhao Li    | Build backend connection; Visual Assessment Algorithm.       |
| Junqi Chen   | Collaborate with Yangqin Yan to work on frontend layout design and frontend activities implementation. The original repo of frontend is shown here: https://github.com/yangqin-yan/VE441_FrontEnd |
| Yuqing Wang  | Collaborate with Minhao Li to work on backend. Allow backend to receive and process audio evaluation request. |
| Yangqin Yan  | Design frontend layout and implement frontend activities.    |
| Jiaming Kang |                                                              |

## Dependencies

### Audio API
+ `numpy`
+ `librosa`
+ `pickle`

### Content API
+ `nltk`
