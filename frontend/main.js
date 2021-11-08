const app = Vue.createApp({
    data() {
        return {
            shortstories: [],
            home: true,
            read: false,
            changeInput: false,
            shortstory: {
                id: '',
                author: '',
                creationDate: '',
                title: '',
                genre: '',
                text: ''
            }
        }
    },
    created() {
        axios
            .get('http://localhost:8080/shortstories/')
            .then((response) => {
                console.log(response.data)
                this.shortstories = response.data
            })
            .catch((error) => {
                console.log(error)
            })
    },
    methods: {
        showdetail(id, title, author, creationDate, genre, text){
            this.shortstory.id = id
            this.shortstory.title = title
            this.shortstory.author = author
            this.shortstory.creationDate = creationDate
            this.shortstory.genre = genre
            this.shortstory.text = text
            console.log("selectet: ",this.shortstory.id)
            this.home = false
            this.read = true
            //console.log(this.shortstory.title)
        },
        onPost() {
            if (this.shortstory.genre == '' || this.shortstory.text == '' || this.shortstory.title == '' || this.shortstory.author == '') {
                console.log('Input-Error')
            }  
            else {
                this.shortstory.shift
                console.log(this.shortstory)
                axios.post("http://localhost:8080/shortstories/",
                    this.shortstory)
                    .then(result => {
                        console.log(result)
                        this.statuscode = result.status
                    })
                    .catch(error =>
                        console.log(error))
            }

        },
        onChange(){
                if (this.shortstory.id == '')
                {
                    console.log(this.shortstory.title, this.shortstory.text)
                    this.onPost()
                } 
                if (this.shortstory.id != '') {
                    console.log('put id:', this.shortstory.id)
                    let put = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre, 
                    creationDate: this.creationDate,
                    text: this.shortstory.text
                    };
                    axios.put("http://localhost:8080/shortstories/"+ this.shortstory.id , put).then((result) => {
                    console.log(result);
                    })
                }
                
        },
        onDelete(id){
            console.log(id)
            axios.delete("http://localhost:8080/shortstories/"+ id).then((result) => {
                console.log(result);
            });
        },
        change() {
            this.read = false
            this.home = false
            this.changeInput = true
        },
        backHome(){
            this.read = false
            this.changeInput = false 
            this.home = true
        },
        newStory(){
            this.shortstory.id = ''
            this.shortstory.title = ''
            this.shortstory.author = ''
            this.shortstory.creationDate = new Date(Date.now()).toLocaleDateString()
            this.shortstory.genre = ''
            this.shortstory.text = ''
            this.change()
        }
    }
})
