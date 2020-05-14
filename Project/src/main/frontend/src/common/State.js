import React from "react";
import {

    FaRegClock,


} from 'react-icons/fa'

export const STATE ={
    cancel: "cancel",
    refused: "refused",
    accepted:"accepted",
    pending:"pending",
    passed : "passed"
}
export function transformState(state, idx){
    let stateInfo = [];
    switch(state){
        case "pending":
            stateInfo= [ <FaRegClock key={idx}/>, " En attente"]
            break
        case "accepted":
            stateInfo=[<FaRegClock key={idx}/>," Accepté"]
            break
        case "refused":
            stateInfo=[ <FaRegClock key={idx}/>," Refusé"]
            break
        case "cancel":
            stateInfo= [<FaRegClock key={idx}/>," Annulé"]
            break
        case "passed":
            stateInfo = [<FaRegClock key={idx}/>," Passé"]
            break;
    }
    return stateInfo
}