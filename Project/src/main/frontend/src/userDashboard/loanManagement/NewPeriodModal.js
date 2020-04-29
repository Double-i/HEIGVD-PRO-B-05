import React, {Component, useState} from 'react'
import {Button, Container, Modal} from 'react-bootstrap'
import * as moment from 'moment'
import DatePicker from 'react-date-picker'

function NewPeriodModal(props) {
    const [selectedDate, setSelectedDate] = useState(new Date())

    // The minimun date is either today or the valid period date + 1 day (to avoid to give back the tool the same day..)
    const dateStartPlusDay = moment(props.loan.validPeriod.dateStart).add(1,'days')
    const dateEndLessDay = moment(props.loan.validPeriod.dateEnd).subtract(1, 'days')
    const tomorrow =  moment().add(1,'days')

    // min date : plus grand que date start et plus grand que aujourd'hui
    const minDate = tomorrow.isAfter(dateStartPlusDay) ? new Date(tomorrow) : new Date(dateStartPlusDay)
    const maxDate = dateEndLessDay.isAfter( dateStartPlusDay) ? new Date(dateEndLessDay) : new Date(dateStartPlusDay.add(1,'days'))


    console.log("NEW PERIOD ", minDate, maxDate)

    return (
        <Modal
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
            onHide={props.onHide}
            show={props.show}
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    <p>Choisissez Une date</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>Cette date ne peut être inféreur à la date de début de la location, ni être supérieur à celle de fin couramment valide</p>
                <DatePicker value={selectedDate}  minDate={minDate} maxDate={maxDate} onChange={date => {
                    setSelectedDate(date)
                }}
                />
                <Button   variant="outline-primary" onClick={()=>{
                    props.addPeriod(props.loan, selectedDate)

                }}>Ajouter</Button>

            </Modal.Body>
        </Modal>
    )
}

export default NewPeriodModal
