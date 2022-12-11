import json

from revChatGPT.revChatGPT import Chatbot
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
        parent_id = args['parent_id']
        config = json.loads(args['chatgpt-config'])
        chatbot = Chatbot(config, conversation_id, parent_id, refresh=False)
        response = chatbot.get_chat_response(prompt)
        print(response)
        return response, 200


class Login(Resource):

    def post(self):
        args = parser.parse_args()
        print(args)
        config = json.loads(args['chatgpt-config'])
        chatbot = Chatbot(config, refresh=True)
        response = chatbot.config
        print(response)
        return response, 200


api.add_resource(Chat, '/chat')
api.add_resource(Login, '/login')

if __name__ == '__main__':
    app.run(debug=True)
