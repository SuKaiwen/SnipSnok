import React, { useState, useEffect } from 'react'
import { Container, Row, Col } from 'react-bootstrap';
import {searchMedia, searchArtists} from '../../services/axios/search'
import MediaCard from './MediaCard'
import ArtistCard from './ArtistCard'
import ContentCard from '../contents/ContentCard'

const SearchResults = (props) =>{
    const [artists, setArtists] = useState([]);
    const [media, setMedia] = useState([]);

    useEffect(() => {
        let meduaResponse = searchMedia(props.match.params.query);
        let artistResponse = searchArtists(props.match.params.query)
        meduaResponse.then((res)=>{setMedia(res)})
        artistResponse.then((res)=>{setArtists(res)})
    }, [props.match.params.query])

    useEffect(() => {

    }, [media, artists])

    const renderArtists = () =>{
        if(artists.length>0){
            return artists.map(artist=>{
                return <ArtistCard artist={artist} key={artist.id}/>
            })
        }
        else{
            return(
                <h3> No artists found... </h3>
            )
        }
    }

    const renderMedia = () =>{
        if(media.length>0){
            return media.map(content=>{
                return <MediaCard content={content} key={content.id}/>
            })
        }
        else{
            return(
                <h3> No media found... </h3>
            )
        }
    }
        return (
            <div>
                <Container>
                    <Row>
                        <Col>
                            <h1>Media</h1>
                            {renderMedia()}
                        </Col>
                        <Col>
                            <h1>Creators</h1>
                            {renderArtists()}
                        </Col>
                    </Row>
                </Container>
            </div>
        )
}

export default SearchResults