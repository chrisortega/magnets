import os
from google.cloud import pubsub_v1

credentials_path = '/Users/ivangonzalez/Documents/magnet_js/neon-mote-358900-66ed0a8a8cd0.json'
os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = credentials_path


publisher = pubsub_v1.PublisherClient()
topic_path = 'projects/neon-mote-358900/topics/magnetos'


data = 'A garden sensor is ready!'
data = data.encode('utf-8')
attributes = {
    'sensorName': 'garden-001',
    'temperature': '75.0',
    'humidity': '60'
}

future = publisher.publish(topic_path, data, **attributes)
print(f'published message id {future.result()}')
