import React, {useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Form, Button, Spinner, Alert} from 'react-bootstrap'
import {Formik} from 'formik'
import * as yup from 'yup'
import regex from '../../common/regex'
import {checkAddress} from '../../common/GoogleApiHelper'
import {sendEzApiRequest, sendRequest} from '../../common/ApiHelper'
import {VALIDATION_MSG} from "../../common/ValidationMessages";
import {formatString} from "../../common/Utils";

function ProfilForm(props) {

    const [isSendingForm, setIsSendingForm] = useState(false)
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenSaved, setHasBeenSaved] = useState(false)
    const [isValidAddress, setIsValidAddress] = useState(true)

    const attemptSignUp = values => {
        setIsValidAddress(true)
        setHasBeenSaved(false)
        setHasConnectionProblem(false)
        checkAddress({
            address: values.userAddress,
            npa: values.userNpa,
            city: values.userCity,
            country: values.userCountry,
        }).then(([isValid, googleAddress]) => {
            if (isValid === 1) {
                setIsValidAddress(true);

                let verb

                const profilData = {
                    firstName: values.userFirstname,
                    lastName: values.userLastname,
                    email: values.userEmail,
                    address: {
                        address: `${googleAddress.street} ${googleAddress.streetNumber}`,
                        district: values.userDistrict,
                        postalCode: googleAddress.zipCode,
                        lat: googleAddress.lat,
                        lng: googleAddress.long,
                        city: {
                            city: googleAddress.city,
                            country: {
                                country: googleAddress.country
                            }
                        }
                    }
                }
                if (!props.editProfil) {
                    verb = "POST"
                    profilData.password = values.userPassword
                    profilData.userName = values.userName
                }else{
                    verb = "PUT"
                    // if we edit the profil we need the address'id
                    profilData.address.id = props.initialValues.userAddressId
                }
                setIsValidAddress(true)

                sendEzApiRequest(props.endpoint, verb, profilData).then(
                    result => {
                        // en principe seul les requete http 200 passe ici
                        console.log('Connection ok et réponse du serveur, ' + values)
                        setIsSendingForm(false)
                        setHasBeenSaved(true)
                        props.afterEditCb(values)
                    },
                    error => {
                        console.log(error);
                        // en principe requete à problemes ici => code http 400 & 500 ou pas de co au serveur
                        setIsSendingForm(false)
                        setHasConnectionProblem(true)

                    }
                );
            } else {
                setIsSendingForm(false)
                setIsValidAddress(false)
            }
        });
    }
    const schemaSpec = {

        userEmail: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .email(VALIDATION_MSG.email),
        userFirstname: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(20, formatString(VALIDATION_MSG.max, 20))
            .matches(regex.validName, VALIDATION_MSG.usernameRegex ),
        userLastname: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(20)
            .matches(regex.validName),

        userAddress: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(50),
        userNpa: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(20, formatString(VALIDATION_MSG.max, 20)),
        userDistrict: yup
            .string()
            .required(VALIDATION_MSG.requis),
        userCity: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(50, formatString(VALIDATION_MSG.max, 20)),
        userCountry: yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(2, formatString(VALIDATION_MSG.min, 2))
            .max(50, formatString(VALIDATION_MSG.max, 20)),
    }
    // If the form is used for signup we add spec for username and password
    // which are not needed for editing profil
    if (!props.editProfil) {

        schemaSpec.userName = yup
            .string()
            .required(VALIDATION_MSG.requis)

        schemaSpec.userPassword = yup
            .string()
            .required(VALIDATION_MSG.requis)
            .min(8, formatString(VALIDATION_MSG.min, 8))
            .max(32, formatString(VALIDATION_MSG.max, 32))

        schemaSpec.userPasswordRepeat = yup
            .string()
            .required(VALIDATION_MSG.requis)
            .oneOf(
                [yup.ref('userPassword'), null],
                formatString(VALIDATION_MSG.same, "mot de passe")
            )
    }

    // validation rules
    const schema = yup.object(schemaSpec)

    return (
        <>
            <Alert variant="success" hidden={!hasBeenSaved}>
                Edition du profil enregistrée
            </Alert>
            <Alert variant="danger" hidden={!hasConnectionProblem}>
                hum.. problème de connexion au serveur
            </Alert>
            <Alert variant="warning" hidden={isValidAddress}>
                L'adresse semble incorrecte ou peu précise veuillez complétez
            </Alert>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    setIsSendingForm(!isSendingForm)
                    attemptSignUp(values)
                }}
                enableReinitialize
                initialValues={props.initialValues}

            >
                {({
                      handleSubmit,
                      handleChange,
                      touched,
                      isValid,
                      values,
                      errors}) => (
                <Form noValidate onSubmit={handleSubmit}>
                    {props.editProfil === false &&
                    <Form.Group controlId="formUsername">
                        <Form.Label>Nom d'utilisateur</Form.Label>
                        <Form.Control
                            name="userName"
                            type="text"
                            placeholder="Nom d'utilisateur"
                            value={values.userName}
                            onChange={handleChange}
                            isValid={touched.userName && !errors.userName}
                            isInvalid={errors.userName}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userName}</Form.Control.Feedback>
                    </Form.Group>
                    }

                    <Form.Group controlId="formEmail">
                        <Form.Label>E-mail</Form.Label>
                        <Form.Control
                            name="userEmail"
                            type="email"
                            placeholder="E-mail"
                            value={values.userEmail}
                            onChange={handleChange}
                            isValid={touched.userEmail && !errors.userEmail}
                            isInvalid={errors.userEmail}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userEmail}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formFirstname">
                        <Form.Label>Prénom</Form.Label>
                        <Form.Control
                            name="userFirstname"
                            type="text"
                            placeholder="Prénom"
                            value={values.userFirstname}
                            onChange={handleChange}
                            isValid={
                                touched.userFirstname &&
                                !errors.userFirstname
                            }
                            isInvalid={errors.userFirstname}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userFirstname}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formLastname">
                        <Form.Label>Nom</Form.Label>
                        <Form.Control
                            name="userLastname"
                            type="text"
                            placeholder="Nom"
                            value={values.userLastname}
                            onChange={handleChange}
                            isValid={
                                touched.userLastname && !errors.userLastname
                            }
                            isInvalid={errors.userLastname}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userLastname}</Form.Control.Feedback>
                    </Form.Group>
                    {/*If we edit profil we don't need password and repeated password*/}
                    {props.editProfil === false &&
                        <>
                    <Form.Group controlId="formPassword">
                        <Form.Label>Mot de passe</Form.Label>
                        <Form.Control
                            name="userPassword"
                            type="password"
                            placeholder="Mot de passe"
                            value={values.userPassword}
                            onChange={handleChange}
                            isValid={
                                touched.userPassword && !errors.userPassword
                            }
                            isInvalid={errors.userPassword}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userPassword}</Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formPasswordRepeat">
                        <Form.Label>
                            mot de passe encore une fois
                        </Form.Label>
                        <Form.Control
                            name="userPasswordRepeat"
                            type="password"
                            placeholder="Retappez le mot de passe"
                            value={values.userPasswordRepeat}
                            onChange={handleChange}
                            isValid={
                                touched.userPasswordRepeat &&
                                !errors.userPasswordRepeat
                            }
                            isInvalid={errors.userPasswordRepeat}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userPasswordRepeat}</Form.Control.Feedback>
                    </Form.Group>
                    </>
                    }
                    <Form.Group controlId="formAddress">
                        <Form.Label>Adresse</Form.Label>
                        <Form.Control
                            name="userAddress"
                            type="text"
                            placeholder="Adresse"
                            value={values.userAddress}
                            onChange={handleChange}
                            isValid={
                                touched.userAddress && !errors.userAddress
                            }
                            isInvalid={errors.userAddress}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userAddress}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formNpa">
                        <Form.Label>Code postal</Form.Label>
                        <Form.Control
                            name="userNpa"
                            type="text"
                            placeholder="Adresse"
                            value={values.userNpa}
                            onChange={handleChange}
                            isValid={touched.userNpa && !errors.userNpa}
                            isInvalid={errors.userNpa}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userNpa}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formDistrict">
                        <Form.Label>District</Form.Label>
                        <Form.Control
                            name="userDistrict"
                            type="text"
                            placeholder="District"
                            value={values.userDistrict}
                            onChange={handleChange}
                            isValid={touched.userDistrict && !errors.userDistrict}
                            isInvalid={errors.userDistrict}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userDistrict}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formCity">
                        <Form.Label>Ville/village</Form.Label>
                        <Form.Control
                            name="userCity"
                            type="text"
                            placeholder="Ville"
                            value={values.userCity}
                            onChange={handleChange}
                            isValid={touched.userCity && !errors.userCity}
                            isInvalid={errors.userCity}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userCity}</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formCountry">
                        <Form.Label>Pays</Form.Label>
                        <Form.Control
                            name="userCountry"
                            type="text"
                            placeholder="Pays"
                            value={values.userCountry}
                            onChange={handleChange}
                            isValid={
                                touched.userCountry && !errors.userCountry
                            }
                            isInvalid={errors.userCountry}
                        />
                        <Form.Control.Feedback type="invalid">{errors.userCountry}</Form.Control.Feedback>
                    </Form.Group>

                    <Button
                        variant="primary"
                        type="submit"
                        size="lg"
                        block
                        disabled={isSendingForm}
                    >
                        {props.editProfil ? "Modifier" :"S'inscrire"}
                        <Spinner
                            as="span"
                            animation="border"
                            size="sm"
                            role="status"
                            aria-hidden="true"
                            hidden={!isSendingForm}
                        />
                    </Button>
                </Form>)}
            </Formik>
        </>
    )
}

export default ProfilForm
