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
                let post = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre, 
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                    }
                console.log(post)
                axios.post("http://localhost:8080/shortstories",
                    post)
                    .then(result => {
                        console.log(result)
                        this.statuscode = result.status
                        this.backHome()
                        location.reload()
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
                    creationDate: new Date(Date.now())
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
                this.backHome();
                location.reload()
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
            this.shortstory.creationDate = new Date(Date.now())
            this.shortstory.genre = ''
            this.shortstory.text = ''
            this.change()
        }
    }
})
