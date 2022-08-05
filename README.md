# 441-team-project

## Getting Started

The link to all 3rd-party tools, libraries, SDKs, APIs that may be used in our project are listed below:

+ **Speech to Text API**: [Assembly AI Speech-to-Text](https://www.assemblyai.com/)
+ **Content Analysis**: [NLTK (Natural Language Toolkit)](https://www.nltk.org/)
+ **Audio Analysis**: [Librosa](https://librosa.org/doc/latest/index.html)
+ **Tone Analysis**: [torchaudio](https://pytorch.org/audio/stable/index.html)
+ **Pretrained Multi-task Cascaded Convolutional Networks**: [fer-pytorch](https://pypi.org/project/fer-pytorch/)
+ **Hand Motion Track**: [mediapipe](https://google.github.io/mediapipe/)
+ **Neural Network Framework**: [Pytorch](https://pytorch.org/)
+ **Computer Vision Toolkit**: [OpenCV](https://pypi.org/project/opencv-python/)

### Setting-up Environment

#### Backend Server
The backend server relies on the following packages. Please use pip to install them.
+ `requests`
+ `flask`
+ `ffmpy`

#### Audio API
The audio API relies on the following packages. Please use pip to install them.
+ `numpy`
+ `librosa`
+ `pickle`

#### Content Analysis API
The content analysis API relies on the following package. Please use pip to install them.
+ `nltk`

After installing `nltk`, please run `nltk.download('stopword')` in a python shell. Then, if running a linux machine, please run `flask/api/content/download.sh` directly to download pretrained word embedding files.

#### Frontend APP
If using a virtual device from android studio, please make sure your versions are: 

### Running the APP
For backend server, run `python flask/api/api.py`. Please ensure the port number is not occupied. Then before running the frontend, please change the server url accordingly. After changing the url, launch the APP and enjoy!


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
| `topic`    |  String | Presentation Topic                           |
| `script`    |  String | Presentation script                         |
| `recording_path` |  String | Location of Rehearsal Recording Uploaded by User |
| `recording`  | Media File | Rehearsal Recording  |

**Speech-to-text Request Parameters**

| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `uri`    |  String | Path to Audio File                            |
| `encoding` |  String | Type of Audio Encoding |
| `languageCode`  | String | Audio Language  |
| `sampleRateHertz`  | String | Sampling rate of audio  |

**Speech-to-text Respond Parameters**

| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `transcript`    |  String | Transcripts of Audio File                           |
| `confidence` |  Float | Confidence value (0-1)|
| `word`  | dict | Start Time and Stop Time of Each Word in Transcript  |

**Back-End Respond Parameters**
| Key           |  Type   | Description                              |
| ------------- |  ------ | ---------------------------------------- |
| `gesture_score`    |  float | 0-10 Score which evaluates users' gesture usages                         |
| `facial_score`    |  float | 0-10 Score which evaluates users' facial expressions                        |
| `tone_score`    |  float | 0-10 Score which evaluates users' tone                       |
| `stopword_count`    |  int | Total Stop Words Counts of Recording                         |
| `volume_score` |  Float | 0-10 Score which evaluates users' speaking volume |
| `recite_score` |  Float | 0-10 Score which evaluates users' articulation and recitation of scripts |
| `feedback`  | String | Summary of various scores and includes a short instruction on how to improve the overall presentations.  |

## View UI/UX

This section is left blank for now.

## Team Roster

The contribution division is detailed in the table below.

| Name         | Contribution                                                 |
| ------------ | ------------------------------------------------------------ |
| Chenshu Zhu  | Build presentation audio rating api. Build content-topic correlation rating api. Build speech-to-text API. Manage git repo. |
| Minhao Li    | Build backend connection; Visual Assessment Algorithm.       |
| Junqi Chen   | Collaborate with Yangqin Yan to work on frontend layout design and frontend activities implementation. The original repo of frontend is shown here: https://github.com/yangqin-yan/VE441_FrontEnd |
| Yuqing Wang  | Collaborate with Minhao Li to work on backend. Allow backend to receive and process audio evaluation request. |
| Yangqin Yan  | Design frontend layout and implement frontend activities.    |
| Jiaming Kang |                                                              |

### Challenges in APP Development 

Throughout the APP development, we met numerous challenges. Yet, all of our challenges can be summarized into "a difficulty in time estimation and project planning". In the frontend part, our unfamiliarity with `kotlin` syntax resulted in time debugging codes involving the okhttp and other 3rd party APIs. We had ZOOM meetings to pool our efforts in debugging, which greatly accelerated the debugging process. In the backend development part, our greatest obstacle was the insufficient computing power provided by the aws EC2 server. Since our project involves machine learning models, the memory on the server soon proved to be not enough. Therefore, we had to develop concurrently on our local laptops and on the cloud, configuring every environment twice to figure out whether any bug was due to memory deficiency or syntatic errors. This helped us learn the importance of documenting our environment configuration. While developing the features, we spent more time than expected reearching existing APIs and open source libraries. Whenever we choose to use an opensource library with state-of-the-art performance, it would involve environment configuration. And if using an API, we need to spend time reading the documentation. Comparing APIs and open source libraries therefore became time-consuming. In retrospect, if we had assigned one member to test each API or library concurrently, we would have saved time. Another lesson learned. While developing ML features, such as hand-gesture and facial expression recognition, we spent much time again preparing environments and running experiments. However, this time seems unavoidable. If anything, we could have done this part as early as possible. In summary, due to our lack of experience in team-coding, we wasted some time in almost every stage of our development. However, after each development process, we learned something that can help us acomplish these tasks faster in the future. We even had the chane of puttong some skills to practise this time. 
