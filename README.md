# 441-team-project

(I simply copy and paste the template here, you can modified it.)

## Getting Started

The link to all 3rd-party tools, libraries, SDKs, APIs that may be used in our project are listed below:

+ **Speech to Text API**: [Google Cloud Speech-to-Text](https://cloud.google.com/speech-to-text)
+ **Audio Analysis**: [Librosa](https://librosa.org/doc/latest/index.html)
+ **Tone Analysis**: [torchaudio](https://pytorch.org/audio/stable/index.html) 
+ ...

## Model and Engine

[story map]

[explanation]

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

