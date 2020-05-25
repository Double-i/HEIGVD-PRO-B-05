
import React from "react"
import Conversation from "./Conversation";
import {sendEzApiRequest} from "../common/ApiHelper";



class ConversationList  extends React.Component {

    constructor(props) {
        super(props);

        this.openButtonStyle =
            {

                position: "fixed",
                bottom: 30,
                right: 30,
                borderRadius: 100,

            }
        this.style =
            {
                paddingTop: 10,
                paddingLeft: 20,
                paddingRight: 20,
                paddingBottom: 20,
                bottom: 100,
                right: 100,
                position: "absolute",
                width: 500,
                maxHeight: 500,
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
                <p>Mes conversations</p>
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
                                    (
                                    <li className="nav-item" key={idx}>
                                        <a className="nav-link"
                                            onClick=
                                            {
                                                ()=>
                                                {
                                                    this.displayConversationContent(conversation)
                                                }
                                            }>
                                        {conversation.convName}
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
                <span style={this.openButtonStyle} >
                    <div className="speechBubble"><img src="https://img.icons8.com/color/48/000000/speech-bubble-with-dots.png" onClick ={() => {this.toggle()}}/></div>
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
                </span>

            </div>
        )

    }
}


export default ConversationList