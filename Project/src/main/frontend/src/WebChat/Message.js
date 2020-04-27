
import React from "react"

class Message extends React.Component
{

    constructor(props)
    {
        super(props)
        this.state =
            {
                content:props.content,
                date:props.date
            }
    }

    render()
    {
        return (
            <>
                <p>{this.state.content}</p>
                <div>{this.state.date}</div>
            </>
        )
    }
}

export default Message