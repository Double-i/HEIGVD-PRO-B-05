
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
                currentConversation: [],
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
            let url = "/loans/find/user/"+this.state.currentUser
            sendEzApiRequest(url,"GET",{},{borrower:false}).then(
                (result) => {
                    this.setState({
                        currentConversation:result,
                        dataLoaded:true
                    })

                },
                (error) => {
                    console.log(error)
                }
            )
        }
        return (
            <div class = "container bg-secondary" style={this.style}>
                <p class >My conversations</p>
                <div class = "row">
                    <div className="col-sm-4">
                        {this.displayConversations()}
                    </div>
                    <div className="col-sm-8 bg-primary ">
                        {console.log(this.state.currentConversation[0])}

                        {
                            this.state.currentConversation.length > 0 ?
                                (
                                    this.displayConversationContent(this.state.currentUser,this.state.currentConversation[0].borrower.userName)

                                ):(
                                    <></>
                                )
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
                            {
                                this.state.currentConversation.map((value, idx) => (
                                <li className="nav-item">
                                    <a className="nav-link"
                                        onClick=
                                            {
                                                ()=>
                                                {
                                                    //TODO display message of current conversation
                                                }
                                            }>
                                        {value.borrower.userName}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </nav>

                </>
            )
    }

    displayConversationContent(sender,recipient) {

        return   (<Conversation sender={sender }recipient ={ recipient}/>)
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