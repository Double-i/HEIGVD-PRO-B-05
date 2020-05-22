import React from "react";
import {
    FaCheck,
    FaBan,
    FaRegClock,
    FcCancel,
    FaCross


} from 'react-icons/fa'
import {GiCancel} from "react-icons/gi";

/**
 * Different state a loan or a period can have
 * @type {{cancel: string, refused: string, pending: string, accepted: string, passed: string}}
 */
export const STATE ={
    cancel: "cancel",
    refused: "refused",
    accepted:"accepted",
    pending:"pending",
    passed : "passed"
}

/**
 * return the icon and the french name for a state.
 *
 * @param state we like to get
 * @param idx use to give a key. See React key for component display in list
 * @returns {[]} [0] => Icon, [1] => label
 */
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