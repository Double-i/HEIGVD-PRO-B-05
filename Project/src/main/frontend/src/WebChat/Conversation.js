import React from "react"
import Message from "./Message";
import Button from "react-bootstrap/Button";
const SockJS = require("sockjs-client")
const Stomp = require( "@stomp/stompjs")
class Conversation extends React.Component
{


    constructor(props) {
        console.log(props)
        super(props);

        this.state = {
            active:false,
            sender: props.sender,
            recipient: props.recipient,
            messageList : []
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

    componentWillReceiveProps(nextProps, nextContext) {

         this.socket = new SockJS("http://localhost:8080/chat");
         this.client = Stomp.Stomp.over(this.socket);


         //disconect before
        if(this.client !== null)
        {
            this.client.disconnect();
            console.log("disconnected");
        }

        this.client.connect({},(frame)=>{

            console.log("connected " + frame)
            this.client.subscribe('/topic/messages', (content) =>
            {
                console.log(content.body)
                this.showMessageOutput(JSON.parse(content.body))
            })

        })

        console.log(nextProps)
        let messages = [];
        for(let i = 0; i < 10; i++)
        {
            i%2 ?
                messages.push(
                    <li className="list-group-item text-right" style={this.senderStyle}> {nextProps.sender}
                        <Message content={"i send this"} date={"sometime"}/>
                    </li>
                ):
                messages.push(
                    <li className="list-group-item text-left" style={this.recipientStyle}> {nextProps.recipient}
                        <Message content={"he send that"} date={"sometime"}/>
                    </li>
                )
        }
        this.setState({messageList:messages})

    }

    showMessageOutput(output)
    {
        let message = (
            <li className="list-group-item text-right" style={this.senderStyle}> {output.sender}
                <Message content={output.content} date={"sometime"}/>
            </li>)


        this.setState({messageList:[...this.state.messageList,message]})
    }
    componentDidMount()
    {
        let messages = [];
        for(let i = 0; i < 10; i++)
        {
            i%2 ?
            messages.push(
                <li className="list-group-item text-right" style={this.senderStyle}> {this.state.sender}
                    <Message content={"i send this"} date={"sometime"}/>
                </li>
            ):
            messages.push(
                <li className="list-group-item text-left" style={this.recipientStyle}> {this.state.recipient}
                    <Message content={"he send that"} date={"sometime"}/>
                </li>
            )
        }
        this.setState({messageList:messages})

    }

    sendMessage(content_message)
    {

        this.client.send("/app/chat",{},JSON.stringify(
            {
                content : content_message,
                sender: this.state.sender,
                recipient : this.state.recipient
            }
        ))
    }
    displayConversation()
    {
        return (
            <div style={this.conversationStyle}>
                 <ul className="list-group overflow-auto " style={this.listGroup}>
                     {this.state.messageList}
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