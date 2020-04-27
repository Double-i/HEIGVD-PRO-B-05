import React from "react"
import Message from "./Message";
import Button from "react-bootstrap/Button";
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

        this.sendMessage = this.sendMessage.bind(this)
    }
    componentWillReceiveProps(nextProps, nextContext) {



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


        let message = (
            <li className="list-group-item text-right" style={this.senderStyle}> {this.state.sender}
                <Message content={content_message} date={"sometime"}/>
            </li>)


        this.setState({messageList:[...this.state.messageList,message]})

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