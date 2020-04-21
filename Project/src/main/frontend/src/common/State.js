import React from "react";
import {
    FaUser,
    FaRegClock,
    FaRegCalendarAlt,
    FaLocationArrow,
} from 'react-icons/fa'

export const STATE ={
    cancel: "cancel",
    refused: "refused",
    accepted:"accepted",
    pending:"pending",
    passed : "passed"
}
export function transformState(state){
    let stateInfo = [];
    switch(state){
        case "pending":
            stateInfo= [ <FaRegClock/>, " En attente"]
            break
        case "accepted":
            stateInfo=[<FaRegClock/>," Accepté"]
            break
        case "refused":
            stateInfo=[ <FaRegClock/>," Refusé"]
            break
        case "cancel":
            stateInfo= [<FaRegClock/>," Annulé"]
            break
        case "passed":
            stateInfo = [<FaRegClock/>," Passé"]
            break;
    }
    return stateInfo
}