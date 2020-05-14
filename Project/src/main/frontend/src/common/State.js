import React from "react";
import {
    FaCheck,
    FaBan,
    FaRegClock,
    FcCancel,
    FaCross


} from 'react-icons/fa'
import {GiCancel} from "react-icons/gi";

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
            stateInfo=[<FaCheck key={idx}/>," Accepté"]
            break
        case "refused":
            stateInfo=[ <FaBan key={idx}/>," Refusé"]
            break
        case "cancel":
            stateInfo= [<GiCancel key={idx}/>," Annulé"]
            break
        case "passed":
            stateInfo = [<FaCross key={idx}/>," Passé"]
            break;
    }
    return stateInfo
}