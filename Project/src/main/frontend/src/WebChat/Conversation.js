import React from "react"
import Message from "./Message";
import Button from "react-bootstrap/Button";
import {sendEzApiRequest} from "../common/ApiHelper";
const SockJS = require("sockjs-client")
const Stomp = require( "@stomp/stompjs")


/**
 * A conversation between two Users
 * props contains
 *  - Current user - taken from session
 *  - a conversation object
 *      - wich contains :
 *          * a Map with two participants
 *          * Id of the conversation
 *          * Id of the loan that it concerns ( Subject of the conversation )
 */
class Conversation extends React.Component
{
    constructor(props) {
        super(props);
        this.state = {
            sender: "",
            recipient: "",
            messageList: [],
            currentUser: props.currentUser,
        };

        this.conversationStyle =
            {
                padding: 5
            };

        //css for the message UL
        this.listGroupStyle =
            {
                maxHeight: 300,
                padding: 5
            };

        //Message styling for the sender
        this.senderStyle =
            {
                backgroundColor: "#A4EEFF",
                borderRadius: 10,
                margin: 10
            };

        //Message Styling for the recipient
        this.recipientStyle =
            {
                backgroundColor: "#527780",
                borderRadius: 10,
                margin: 10
            };

        //Styling for the send button
        this.sendButton =
            {
                position: "absolute",
                right: 0,
                bottom: 0
            }

        this.socket = null;
        this.client = null;
        this.sendMessage = this.sendMessage.bind(this)

        this.messageRefs = []
    }

    /**
     * When the component will dismount ( switch discussion / close conversations )
     * disconnect the socket
     */
    componentWillUnmount() {
        if(this.client !== null)
        {
            this.client.disconnect();
            this.client = null;
        }


    }

    /**
     * On first load of the conversation ( Opening conversations )
     * Load the messages and connect the websocket
     */
    componentDidMount() {
        this.connect()
        this.loadMessages()

    }

    /**
     *  The component is updated ( The user has switched between conversation )
     * @param prevProps
     * @param prevState
     * @param snapshot
     */
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

    /**
     * Connect the web socket
     */
    connect()
    {
        //disconect before
        if(this.client !== null )
        {
            this.client.disconnect();
            this.client = null;
        }
        if(this.socket !== null)
        {
            this.socket.close();
            this.socket = null;
        }

        //conenct to the websocket server
        this.socket = new SockJS("http://localhost:8080/EZStomp/");
        this.client = Stomp.Stomp.over(this.socket);

        let headers = 
            {
                login:this.state.currentUser,
                "user-name":this.state.currentUser
            }
            
        this.client.connect(headers,(frame)=>{

            //subscribe the client to the specific conversation with the other user
            //the other user will also be subscribed to this conversation if he opens the same conversation
            this.client.subscribe('/secured/user/queue/loan-room/'+this.props.conversation.id, (content) =>
            {
                //each time a message is send  we must display it in the conversation
                this.showMessageOutput(JSON.parse(content.body))
            })
        })
    }

    scroll()
    {
    }
    /**
     * Loads the all the messages from the current conversation
     * //TODO pagination ?
     */
    loadMessages( )
    {

        //Where to look to get all messages related to the conversation
        let endpoint = "/loans/conversations/"+this.props.conversation.id+"/"+this.props.conversation.loan+"/messages/";
        this.setState({messageList:[]})

        //send the request to the API
        sendEzApiRequest(endpoint).then( (result) =>
        {
            let messages = []
            let recipient = this.props.participants.get(this.props.currentUser);
            let sender = this.props.participants.get(recipient);

            let lastMsg = null;
            // for each message returned format them properly
            for(let i  = 0; i < result.length; i++)
            {
                if(i == result.length-1)
                    lastMsg = this.lastMessageRef
                if(result[i].sender === sender)
                {
                    messages.push(

                        <li className="list-group-item text-right" style={this.senderStyle} ref={el => (this.messageRefs[i]) = el} key={i}>
                            {sender}
                            <Message content={result[i].content} date={"sometime"}/>
                        </li>
                    )
                }
                else
                {
                    messages.push(

                        <li className="list-group-item text-left" style={this.recipientStyle} ref={el => (this.messageRefs[i]) = el}>
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
                //TODO proper exception handling
                console.log(error)
            })


    }

    /**
     * Display the message into the conversation
     * @param output object of the message ( Message, Sender, Date )
     */
    showMessageOutput(output)
    {
        let recipient = this.props.participants.get(this.props.currentUser);
        let sender = this.props.participants.get(recipient)
        let messages = null

        if(output.sender === sender)
        {
            messages = (

                <li className="list-group-item text-right" style={this.senderStyle}>
                    {sender}
                    <Message content={output.content} date={"sometime"}/>
                </li>
            )
        }
        else
        {
            messages = (

                <li className="list-group-item text-left" style={this.recipientStyle}>
                    {recipient}
                    <Message content={output.content} date={"sometime"}/>
                </li>
            )
        }

        //append to messages
        this.setState({messageList:[...this.state.messageList,messages]})
    }

    /**
     * Send the message via websocket to the correct destination
     * @param content_message
     */
    sendMessage(content_message)
    {

        let recipient = this.props.participants.get(this.props.currentUser);
        let sender = this.props.participants.get(recipient)

        let message = {
            content : content_message,
            recipient :  recipient,
            sender: sender,
            fkConversation : this.props.conversation
        }
        this.client.send('/EZChat/'+message.fkConversation.id+'/private',{},JSON.stringify(message))
    }


    displayConversation()
    {
        return (
            <div style={this.conversationStyle}>
                 <ul className="list-group overflow-auto " style={this.listGroupStyle}>
                     {
                         //display the array of messages
                         this.state.messageList
                     }
                </ul>
                <div>
                    <input id = "message_content"type={"text"}/>
                    <span style={this.sendButton}
                          onClick=
                              { () =>
                                {
                                    this.sendMessage(document.getElementById("message_content").value)  //send the content of the textbox
                                    document.getElementById("message_content").value = "";              //clear the content of the textbox
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