import React from "react"
import Message from "./Message";
import Button from "react-bootstrap/Button";
import {sendEzApiRequest} from "../common/ApiHelper";
const SockJS = require("sockjs-client")
const Stomp = require( "@stomp/stompjs")
class Conversation extends React.Component
{
    constructor(props) {
        super(props);
        this.state = {
            sender: "",
            recipient: "",
            messageList : [],
            currentUser : props.currentUser,
            sessionId : ""
        }

        this.conversationStyle =
            {
                padding : 5
            }
        this.listGroup =
            {
                maxHeight :300,
                padding:5
            }

        this.senderStyle =
            {
                backgroundColor:"#A4EEFF",
                borderRadius:10,
                margin:10
            };
        this.recipientStyle =
            {
                backgroundColor: "#527780",
                borderRadius:10,
                margin:10
            }
        this.sendButton =
            {
                position:"absolute",
                right:0,
                bottom:0
            }
        this.styles = new Map();

        this.styles.set(props.currentUser,this.senderStyle);
        this.styles.set(props.conversation.participants[props.currentUser], this.recipientStyle)

        this.socket = null;
        this.client = null;
        this.sendMessage = this.sendMessage.bind(this)
    }

    componentWillUnmount() {
        if(this.client !== null)
        {
            this.client.disconnect();
            console.log("disconnected");
        }
    }

    connect()
    {
        this.socket = new SockJS("http://localhost:8080/secured/room");
        this.client = Stomp.Stomp.over(this.socket);

        //disconect before
        if(this.client !== null)
        {
            this.client.disconnect();
            console.log("disconnected");
        }
        this.client.connect({},(frame)=>{

            var url = this.client.ws._transport.url;
            console.log(url)
            url = url.replace("ws://localhost:8080/secured/room/","");
            console.log(url)
            url = url.replace("/websocket","");
            console.log(url);
            url = url.replace(/^[0-9]+\//,"");
            console.log(url);
            this.setState({sessionId:url})

            this.client.subscribe('/secured/user/queue/specific-user/'+this.props.conversation.id, (content) =>
            {
                this.showMessageOutput(JSON.parse(content.body))
            })
        })
    }

    componentDidMount() {
        this.connect()
        this.loadMessages()

    }

    loadMessages( )
    {
        let endpoint = "/loans/conversations/"+this.props.conversation.id+"/"+this.props.conversation.loan+"/messages/";
        console.log(endpoint);
        this.setState({messageList:[]})
        sendEzApiRequest("/loans/conversations/"+this.props.conversation.id+"/"+this.props.conversation.loan+"/messages/").then( (result) =>
        {
            console.log()
            let messages = []
            let recipient = this.props.participants.get(this.props.currentUser);
            let sender = this.props.participants.get(recipient);

            console.log("recipient  => "+ recipient);
            console.log("sender => "+sender)
            for(let i  = 0; i < result.length; i++)
            {
                if(result[i].sender === sender)
                {
                    messages.push(

                        <li className="list-group-item text-right" style={this.senderStyle}>
                            {sender}
                            <Message content={result[i].content} date={"sometime"}/>
                        </li>
                    )
                }
                else
                {
                    messages.push(

                        <li className="list-group-item text-left" style={this.recipientStyle}>
                            {recipient}
                            <Message content={result[i].content} date={"sometime"}/>
                        </li>
                    )
                }


            }
            this.setState({messageList:messages});
        },
            (error) =>
            {
                console.log(error)
            })


    }
    componentDidUpdate(prevProps, prevState, snapshot) {

        //avoid infinite looping
        if(this.props.conversation.participants !== prevProps.conversation.participants)
        {
            this.setState({
                sender:this.props.participants[this.state.currentUser],
                recipient:this.props.participants[this.state.sender]
            })
            this.connect();
            this.loadMessages()
        }
    }

    showMessageOutput(output)
    {
        let rec = this.props.participants.get(this.props.currentUser);
        let send = this.props.participants.get(rec)


        let messages = null
        if(output.sender === send)
        {
            messages = (

                <li className="list-group-item text-right" style={this.senderStyle}>
                    {send}
                    <Message content={output.content} date={"sometime"}/>
                </li>
            )
        }
        else
        {
            messages = (

                <li className="list-group-item text-left" style={this.recipientStyle}>
                    {rec}
                    <Message content={output.content} date={"sometime"}/>
                </li>
            )
        }

        //append to messages
        this.setState({messageList:[...this.state.messageList,messages]})
    }

    sendMessage(content_message)
    {

        let rec = this.props.participants.get(this.props.currentUser);
        let send = this.props.participants.get(rec)

        console.log(this.props)
        let message = {
            content : content_message,
            recipient :  rec,
            sender: send,
            fkConversation : this.props.conversation
        }
        console.log(JSON.stringify(message))
        this.client.send('/secured/user/queue/specific-user/'+this.props.conversation.id,{},JSON.stringify(message))
    }
    displayConversation()
    {
        return (
            <div style={this.conversationStyle}>
                 <ul className="list-group overflow-auto " style={this.listGroup}>
                     {
                         this.state.messageList
                     }
                </ul>
                <div>

                    <input id = "message_content"type={"text"}/>
                    <span style={this.sendButton}
                          onClick=
                              { () =>
                                {
                                    this.sendMessage(document.getElementById("message_content").value)
                                    document.getElementById("message_content").value = ""
                                }
                            }>
                        <img src="https://img.icons8.com/material-sharp/24/000000/filled-sent.png"/>
                    </span>
                </div>
            </div>

        )
    }
    render()
    {
        return (
            <div>
                {this.displayConversation()}
            </div>
        )
    }
}


export default Conversation