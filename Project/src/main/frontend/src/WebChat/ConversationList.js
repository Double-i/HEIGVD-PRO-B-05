
import React from "react"
import Conversation from "./Conversation";
import {sendEzApiRequest} from "../common/ApiHelper";



class ConversationList  extends React.Component {

    constructor(props) {
        super(props);

        this.openButtonStyle =
            {
                width: 100,
                height: 100,
                position: "absolute",
                bottom: 0,
                right: 0,
                borderRadius: 100,

            }
        this.style =
            {
                paddingLeft: 20,
                paddingRight: 20,
                paddingBottom: 20,
                bottom: 100,
                right: 100,
                position: "absolute",
                maxWidth: 500
            };

        this.state =
            {
                currentUser: props.currentConnected.userInfo.username,
                opened: false,
                dataLoaded : false,
                ongoingConversations: [],
                currentConversation : null,
            };
    }

    toggle() {
        let currentState = this.state.opened;
        this.setState({opened: !currentState,dataLoaded:false})
    }


    openConversation()
    {
        //fetch
        if(this.state.dataLoaded === false)
        {
            let url = "/loans/conversations/"+this.state.currentUser
            sendEzApiRequest(url,"GET",{},{borrower:false}).then(
                (result) => {
                    this.setState({
                        ongoingConversations:result,
                        dataLoaded:true
                    })

                },
                (error) => {
                    console.log(error)
                }
            )
        }
        return (
            <div className = "container bg-secondary" style={this.style}>
                <p>My conversations</p>
                <div className = "row">
                    <div className="col-sm-4">
                        {this.displayConversationsList()}
                    </div>
                    <div className="col-sm-8 bg-primary ">
                        {
                                this.state.currentConversation
                        }
                    </div>
                </div>
            </div>
        )
    }

    displayConversationsList()
    {
            return(
                <>
                    <nav className="navbar  bg-dark navbar-dark">
                        <ul className="navbar-nav">
                            {
                                this.state.ongoingConversations.map((conversation, idx)  =>
                                this.state.currentUser === conversation.participants[0] ?
                                    (
                                    <li className="nav-item" key={idx}>
                                        <a className="nav-link"
                                            onClick=
                                            {
                                                ()=>
                                                {
                                                    //TODO display message of current conversation
                                                    this.displayConversationContent(conversation)
                                                }
                                            }>
                                        {conversation.participants[1]}
                                        </a>
                                    </li>
                                ):
                                (
                                <li className="nav-item" key={idx}>
                                    <a className="nav-link"
                                        onClick=
                                        {
                                            ()=>
                                            {
                                                //TODO display message of current conversation
                                                this.displayConversationContent(conversation)
                                            }
                                        }>
                                        {conversation.participants[0]}
                                    </a>
                                </li>
                                ))
                            }
                        </ul>
                    </nav>

                </>
            )
    }

    displayConversationContent(conv) {

        let test = new Map();
        test.set(conv.participants[0],conv.participants[1]);
        test.set(conv.participants[1],conv.participants[0]);

        this.setState( {currentConversation: <Conversation conversation = {conv} participants={test} currentUser={this.state.currentUser}/>})
    }
    render()
    {
        return (
            <div>
                <span style={this.openButtonStyle} onClick =
                    {
                        () => {
                            this.toggle()
                        }
                    }>
                    <img src="https://img.icons8.com/carbon-copy/100/000000/chat.png"/>
                </span>
                    {
                        this.state.opened ?
                            (
                                <div>
                                    {this.openConversation()}
                                </div>
                            )
                            :
                            (
                                <div>
                                </div>
                            )
                    }
            </div>
        )

    }
}


export default ConversationList