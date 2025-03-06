import axios from 'axios'
const myaxios = axios.create({
    timeout: 2000
})
export default myaxios