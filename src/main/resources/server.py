import json
import pickle
import base64

from revChatGPT.V3 import Chatbot
from flask import Flask
from flask_restful import reqparse, Api, Resource

# For the config please go here:
# https://github.com/acheong08/ChatGPT/wiki/Setup

app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
parser.add_argument('prompt')
parser.add_argument('conversation_id')
parser.add_argument('parent_id')
parser.add_argument('chatgpt-config', type=str, location='headers')


class Chat(Resource):

    def post(self):
        args = parser.parse_args()
        print(args)

        prompt = args['prompt']
        conversation_id = args['conversation_id']
        config = json.loads(args['chatgpt-config'])

        if conversation_id is None:
            chatbot = Chatbot(api_key=config['password'])
        else:
            chatbot = pickle.loads(base64.b64decode(conversation_id))
        result = chatbot.ask(prompt)
        encode = base64.b64encode(pickle.dumps(chatbot)).decode(encoding='ascii')
        print("result", chatbot.conversation)
        ret = {'message': result, 'conversation_id': encode}
        response = ret
        print(response)
        return response, 200


class Login(Resource):

    def post(self):
        args = parser.parse_args()
        print(args)
        config = json.loads(args['chatgpt-config'])
        chatbot = Chatbot(api_key=config['password'])
        response = config
        print(response)
        return response, 200


api.add_resource(Chat, '/chat')
api.add_resource(Login, '/login')

if __name__ == '__main__':
    app.run(debug=True)
