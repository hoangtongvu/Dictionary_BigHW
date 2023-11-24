// Authorization token that must have been created previously. See : https://developer.spotify.com/documentation/web-api/concepts/authorization
const token = 'BQAswHZcNTNKa64MYd5eCv85Iif9ic49XYiy2qjUZuZNMeYYZlmAIoOQWvjphSLfowNSrS0JjwrEW80gzBhM6QDEarEGSrBqgVnKHK1tfzZrxvTPI7HNtMwxv6ZIdca_T6f3WAe7AFComSWmiU1A0Ed2_smaiZjv2KcM-8BlgpKQo_buC-vp79V5Wd5vKQXh44RzJC7kXKIP8unQJ0Lgj5rYIjPaDBNKI_6Bv_0NoKkDhCa1PnNhv5z00rHN8O9_5xhj7ZxfLcPqxJiv12654K3d';
async function fetchWebApi(endpoint, method, body) {
    const res = await fetch(`https://api.spotify.com/${endpoint}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        method,
        body:JSON.stringify(body)
    });
    return await res.json();
}

async function getTopTracks(){
    // Endpoint reference : https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
    return (await fetchWebApi(
        'v1/me/top/tracks?time_range=long_term&limit=5', 'GET'
    )).items;
}

const topTracks = await getTopTracks();
console.log(
    topTracks?.map(
        ({name, artists}) =>
            `${name} by ${artists.map(artist => artist.name).join(', ')}`
    )
);

//Step 2:
// Authorization token that must have been created previously. See : https://developer.spotify.com/documentation/web-api/concepts/authorization
const token = 'BQAswHZcNTNKa64MYd5eCv85Iif9ic49XYiy2qjUZuZNMeYYZlmAIoOQWvjphSLfowNSrS0JjwrEW80gzBhM6QDEarEGSrBqgVnKHK1tfzZrxvTPI7HNtMwxv6ZIdca_T6f3WAe7AFComSWmiU1A0Ed2_smaiZjv2KcM-8BlgpKQo_buC-vp79V5Wd5vKQXh44RzJC7kXKIP8unQJ0Lgj5rYIjPaDBNKI_6Bv_0NoKkDhCa1PnNhv5z00rHN8O9_5xhj7ZxfLcPqxJiv12654K3d';
async function fetchWebApi(endpoint, method, body) {
    const res = await fetch(`https://api.spotify.com/${endpoint}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        method,
        body:JSON.stringify(body)
    });
    return await res.json();
}

const topTracksIds = [
    '7xcUWyLh6fIMsYjoVI7NnZ','4ewazQLXFTDC8XvCbhvtXs','1abwytAhbWeHrbsA9eODOy','6Ed1q0X8oSKSm4IIhiQbYg','26hOm7dTtBi0TdpDGl141t'
];

async function getRecommendations(){
    // Endpoint reference : https://developer.spotify.com/documentation/web-api/reference/get-recommendations
    return (await fetchWebApi(
        `v1/recommendations?limit=5&seed_tracks=${topTracksIds.join(',')}`, 'GET'
    )).tracks;
}

const recommendedTracks = await getRecommendations();
console.log(
    recommendedTracks.map(
        ({name, artists}) =>
            `${name} by ${artists.map(artist => artist.name).join(', ')}`
    )
);

//Step 3:
// Authorization token that must have been created previously. See : https://developer.spotify.com/documentation/web-api/concepts/authorization
const token = 'BQAswHZcNTNKa64MYd5eCv85Iif9ic49XYiy2qjUZuZNMeYYZlmAIoOQWvjphSLfowNSrS0JjwrEW80gzBhM6QDEarEGSrBqgVnKHK1tfzZrxvTPI7HNtMwxv6ZIdca_T6f3WAe7AFComSWmiU1A0Ed2_smaiZjv2KcM-8BlgpKQo_buC-vp79V5Wd5vKQXh44RzJC7kXKIP8unQJ0Lgj5rYIjPaDBNKI_6Bv_0NoKkDhCa1PnNhv5z00rHN8O9_5xhj7ZxfLcPqxJiv12654K3d';
async function fetchWebApi(endpoint, method, body) {
    const res = await fetch(`https://api.spotify.com/${endpoint}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        method,
        body:JSON.stringify(body)
    });
    return await res.json();
}

const tracksUri = [
    'spotify:track:7xcUWyLh6fIMsYjoVI7NnZ','spotify:track:71BWZa1liIRyUiuJ3MB66o','spotify:track:4ewazQLXFTDC8XvCbhvtXs','spotify:track:0DmkBOGgEWMbUJlL6VwwF2','spotify:track:1abwytAhbWeHrbsA9eODOy','spotify:track:3j4lctlzJqDLNZjTLmc9CV','spotify:track:6Ed1q0X8oSKSm4IIhiQbYg','spotify:track:01z2fBGB8Hl3Jd3zXe4IXR','spotify:track:26hOm7dTtBi0TdpDGl141t','spotify:track:6unndO70DvZfnXYcYQMyQJ'
];

async function createPlaylist(tracksUri){
    const { id: user_id } = await fetchWebApi('v1/me', 'GET')

    const playlist = await fetchWebApi(
        `v1/users/${user_id}/playlists`, 'POST', {
            "name": "My recommendation playlist",
            "description": "Playlist created by the tutorial on developer.spotify.com",
            "public": false
        })

    await fetchWebApi(
        `v1/playlists/${playlist.id}/tracks?uris=${tracksUri.join(',')}`,
        'POST'
    );

    return playlist;
}

const createdPlaylist = await createPlaylist(tracksUri);
console.log(createdPlaylist.name, createdPlaylist.id);

//Step 4:
const playlistId = '4IWWgBiwsH6gbOEYbKv4M9';

<iframe
    title="Spotify Embed: Recommendation Playlist "
    src={`https://open.spotify.com/embed/playlist/4IWWgBiwsH6gbOEYbKv4M9?utm_source=generator&theme=0`}
    width="100%"
    height="100%"
    style={{ minHeight: '360px' }}
    frameBorder="0"
    allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture"
    loading="lazy"
/>