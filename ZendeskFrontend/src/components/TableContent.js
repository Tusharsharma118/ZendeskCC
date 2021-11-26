import axios from "axios";
import { React, useState, useEffect } from "react";
import Table from 'react-bootstrap/Table';
import { Alert, Button, ButtonGroup, Card, Container} from "react-bootstrap";
import './TableContent.css'

const TableContent = () => {
    // set the variables to store values using useState Hook
    const [tickets, setTickets] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const [prev, setPrev] = useState("");
    const [next, setNext] = useState("");
    const [rowRecord, setRowRecord] = useState({});

    // setup the onClick show the pop-up with ticket details
    const [modal, setModal] = useState(false)
    const backendURI = "http://localhost:8080/tickets/"
    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    const showRowDetails = (value) =>  {
       setModal(!modal)
       setRowRecord(value)
    }

    // fetch records from the backend
    const getRecords = async (value) => {
        try {
            const nextCheck = (value === 'prev' ) ? false : true
            // condition to check if it is an initial request
            if (prev === ''){
                const record = await axios.get(backendURI,
                {
                    params:{
                        "nextURI" : "",
                        "prevURI" :"",
                        "next" : nextCheck,
                        "pageSize": 25,
                        "hasNext": true
                    }
                    
                })
                setTickets(record.data.tickets);
                setHasNext(record.data.meta.has_more);
                setPrev(record.data.links.prev);
                setNext(record.data.links.next);
            } else {  
                    const record = await axios.get(backendURI,
                    {
                        params:{
                            "nextURI" : next,
                            "prevURI" :prev,
                            "next" : nextCheck,
                            "pageSize": 25,
                            "hasNext": hasNext
                        }
                        
                    })
                    setTickets(record.data.tickets);
                    setHasNext(record.data.meta.has_more);
                    setPrev(record.data.links.prev);
                    setNext(record.data.links.next);        
            }
        } catch (error) {
            console.log(error);
        }
    };

    // on load this hook will be called and it will fetch the records.
    useEffect(() => {
        getRecords();
    }, []);
    // console.log(tickets[0]?.id)
    return (
        <>  {

            }
            {/* condition to show the pop-up */}
            {
                modal && (
                    <div className="modalBackground">
                        <div className="modalContainer">
                            <Card className="modal-text">
                                <Card.Body>
                                    <Card.Title title = {rowRecord?.subject} >{rowRecord?.id}: {rowRecord?.subject}</Card.Title> 
                                    <Card.Subtitle className="mb-2 ">Status: {rowRecord?.status.toUpperCase()}</Card.Subtitle>
                                    <Card.Text>{rowRecord?.description} <br/><br/> <b>Tags:</b>{rowRecord?.tags?.map((tag,index) => ( <span key={index} className="elements"> {tag} </span>   ))}</Card.Text>
                                    
                                    <Button 
                                        onClick={() => showRowDetails({})}
                                        variant='danger' className="floating-right">
                                        Close
                                    </Button>
                                </Card.Body>
                            </Card>
                        </div>
                    </div>
                )
            }
            <Container>
                <Table striped bordered hover size="sm">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>TITLE</th>
                            {/* <th>Description</th> */}
                            <th>CREATED ON</th>
                            <th>STATUS</th>
                        </tr>
                    </thead>
                    {
                        tickets?.map(record => (
                            <tbody key={record.id}>
                                <tr onClick={() => showRowDetails(record)}>
                                    <td>{record?.id}</td>
                                    <td>{record?.subject}</td>
                                    {/* <td>{record?.description}</td> */}
                                    <td>{new Date(record?.created_at).toLocaleDateString("en-US", options)}</td>
                                    <td>{record?.status.toUpperCase()}</td>
                                </tr>
                            </tbody>
                        ))
                    }
                </Table>
            </Container>
           {tickets.length == 0 &&  <Alert variant='danger'   > 
                    There seems to be a problem with our server! Please try again later!
            </Alert> }

            {tickets.length != 0 && 
                <ButtonGroup className="btn-group-modal mb-2">
                <Button 
                    className="btn-modal"
                    variant='primary'
                    onClick={() => getRecords('prev')}
                    disabled = {tickets[0]?.id > 1  ? false : true}
                >Previous</Button>
                <Button
                    className="btn-modal"
                    variant='primary'
                    onClick={() => getRecords('next')}
                    disabled = {hasNext ? false : true}
                >Next</Button>
            </ButtonGroup>
            }
            
        </>
    )
}

export default TableContent;