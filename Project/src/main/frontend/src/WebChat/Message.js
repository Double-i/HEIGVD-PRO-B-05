
import React from "react"
import * as moment from 'moment'

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
                <p>{
                    moment().isAfter(moment().add(1, "days")) ?
                        (
                            moment(this.state.date).fromNow()
                        ):
                        (
                            moment(this.state.date).format("HH:MM")
                        )

                }</p>
            </>
        )
    }
}

export default Message