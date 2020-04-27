
import React from "react"
import Conversation from "./Conversation";

const SockJS = require("sockjs-client")
const Stomp = require( "@stomp/stompjs")

class ConversationList  extends React.Component
{

    constructor(props) {
        super(props);

        this.openButtonStyle =
            {
                width:100,
                height:100,
                position:"absolute",
                bottom:0,
                right:0,
                borderRadius:100,

            }
        this.style =
        {
            paddingLeft:20,
            paddingRight:20,
            paddingBottom:20,
            bottom : 100,
            right:100,
            position :"absolute",
            maxWidth :500
        };

        this.state =
            {
                opened : false,
                currentConversation:"",
                stomClient : null

            };
        this.socket = null;
        this.client = null;
    }

    toggle()
    {
        let currentState = this.state.opened;

        this.setState({opened :!currentState})

        console.log("open")


    }

    componentWillReceiveProps() {


    }

    openConversation()
    {

        this.socket = new SockJS("http://localhost:8080/chat");

        this.client = Stomp.Stomp.over(this.socket)

        this.client.connect({},()=>{
            console.log("connected")
        })
        return (
            <div class = "container bg-secondary" style={this.style}>
                <p class >My conversations</p>
                <div class = "row">
                    <div className="col-sm-4">
                        {this.displayConversations()}
                    </div>
                    <div className="col-sm-8 bg-primary ">
                        {
                            this.displayConversationContent("manu",this.state.currentConversation)
                        }

                    </div>
                </div>
            </div>
        )
    }

    displayConversations()
    {
            return(
                <>
                    <nav class="navbar  bg-dark navbar-dark">
                        <ul className="navbar-nav">
                            <li className="nav-item"><a className="nav-link"
                            onClick={()=>{this.setState({currentConversation:"manu"})}}>manu</a></li>
                            <li className="nav-item"><a className="nav-link"
                            onClick={()=>{this.setState({currentConversation:"ilias"})}}>ilias</a></li>
                            <li className="nav-item"><a className="nav-link"
                            onClick={()=>{this.setState({currentConversation:"bastien"})}}>bastien</a></li>
                        </ul>
                    </nav>

                </>
            )
    }

    displayConversationContent(sender,recipient) {
        {
            console.log("update")
        }
        return   (<Conversation sender={sender }recipient ={ recipient}/>)
    }
    render()
    {
        return (
            <div>
                <span style={this.openButtonStyle} onClick ={() => this.toggle()}>
                    <img src="https://img.icons8.com/carbon-copy/100/000000/chat.png"/>
                </span>
                    {
                        this.state.opened ?
                            (
                                <p>
                                    {this.openConversation()}
                                </p>
                            )
                            :
                            (
                                <>
                                </>
                            )
                    }
            </div>
        )

    }
}


export default ConversationList