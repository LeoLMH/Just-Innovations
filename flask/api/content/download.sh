if [ ! -d "embeddings" ]
then
    wget https://nlp.stanford.edu/data/glove.6B.zip
    # for windows, simply click the link to download
    unzip glove.6B.zip
    mkdir embeddings
    mv glove.6B.50d.txt embeddings
    rm -f glove.6B.*
fi
