from asyncore import write
from ssl import VerifyMode
import ssl
from uuid import uuid4
import requests
import time
import sys
import wget

import logging

class Magnet:
    def __init__(self) -> None:
        self.headers = {
            
        }
        self.urls = {
            'get_magnets':{
                'url':'https://af323d53243mw3lykc25kn6bnu0gocdg.lambda-url.us-west-1.on.aws/'
            },
            'delete_magnets':{
                'url':'https://a63kwevq4w7nqvv6zzxgvog2xe0wfiun.lambda-url.us-west-1.on.aws/'
            }
        }
        

    def get_pendings_magnets(self):
        body  = {
        'status': {
          'S': "new"
        }
        }
        newmagnets = requests.get(self.urls['get_magnets']['url'],json=body).json()
        # delete all magnets 
        for magnet in newmagnets['Items']:
            #delete magnet, this to avoid over pricing 
            body = {
                "id":str(magnet['id'])
            }
            
            self.save_magnets_to_dir(magnet['magnet'])
            requests.post(self.urls['delete_magnets']['url'],json=body)
            logging.debug(f" deleting {magnet['id']}")
        return newmagnets
        
    def save_magnets_to_dir(self,magnet):
        URL = magnet
        response = requests.get(URL)
        
        open(f"{uuid4()}.torrent", "wb").write(response.content)
        

    def update_status(self):
        requests()

if __name__ == "__main__":
    m = Magnet()
    while True:
        magnets = m.get_pendings_magnets()
        print(magnets)
        time.sleep(600)

        