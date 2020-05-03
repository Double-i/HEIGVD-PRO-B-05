export const NOTIFICATION_STATE = {
    MESSAGE: "MESSAGE",
    SIGNALEMENT: "SIGNALEMENT",
    RESERVATION: "RESERVATION",
    RACCOURCISSEMENT: "RACCOURCISSEMENT",
    ACCEPTATION_DEMANDE_EMPRUNT: "ACCEPTATION_DEMANDE_EMPRUNT",
    REFUS_DEMANDE_EMPRUNT: "REFUS_DEMANDE_EMPRUNT",
    ACCEPTATION_DEMANDE_RACOURCISSEMENT: "ACCEPTATION_DEMANDE_RACOURCISSEMENT",
    REFUS_DEMANDE_RACOURCISSEMENT: "REFUS_DEMANDE_RACOURCISSEMENT"
}
export function notificationRedirectUrl(notification){
    let url;
     switch(notification.state){
         case NOTIFICATION_STATE.MESSAGE:
             // TODO que faire ?
             url = "/home"
             break
         case NOTIFICATION_STATE.SIGNALEMENT:
             // TODO que faire ?
             url = "/home"
             break
         case NOTIFICATION_STATE.RESERVATION:
             url="/dashboard/myloans/owner"
             break
         case NOTIFICATION_STATE.RACCOURCISSEMENT:
             // TODO owner ou borrower ??
             url="/dashboard/myloans/owner"
             break
         case NOTIFICATION_STATE.ACCEPTATION_DEMANDE_EMPRUNT:
         case NOTIFICATION_STATE.REFUS_DEMANDE_EMPRUNT:
             url="/dashboard/myloans/borrower"
             break
         case NOTIFICATION_STATE.ACCEPTATION_DEMANDE_RACOURCISSEMENT:
             // TODO owner ou borrower ?
             break
         case NOTIFICATION_STATE.REFUS_DEMANDE_RACOURCISSEMENT:
             break
         default:
             url = "/home"
             break
     }
     return url
}