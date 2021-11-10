const app = Vue.createApp({
    data() {
        return {
            shortstories: [],
            home: true,
            selector: true,
            read: false,
            changeInput: false,
            genreSearch: '',
            titleSearch: '',
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
            .get('http://localhost:8080/shortstories/' , {params: {limit: 10}})
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
            this.Read()
        },
        onPost() {
            if (this.shortstory.genre == '' || this.shortstory.text == '' || this.shortstory.title == '' || this.shortstory.author == '') {
                alert('Fehlende Pflichtfelder')
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
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                    };
                    axios
                    .put("http://localhost:8080/shortstories/"+ this.shortstory.id , put)
                    .then((result) => {
                        alert('Änderung gespeichert.')
                        console.log(result);
                        this.Read()
                    })
                }
                
        },
        onDelete(id){
            if(confirm('Möchten Sie den Eintrag endgültig löschen?')) {
               axios.delete("http://localhost:8080/shortstories/"+ id).then((result) => {
                console.log(result);
                location.reload()
                }); 
            } else {
                this.Read()
            }    
            
        },

        change() {
            this.read = false
            this.home = false
            this.changeInput = true, 
            this.selector = false
        },
        Home(){
            location.reload()
            this.read = false
            this.changeInput = false 
            this.home = true, 
            this.selector = true
        },
        Read(){
            this.home = false
            this.read = true
            this.changeInput = false, 
            this.selector = false 
        },
        newStory(){
            this.shortstory.id = ''
            this.shortstory.title = ''
            this.shortstory.author = ''
            this.shortstory.creationDate = new Date(Date.now())
            this.shortstory.genre = ''
            this.shortstory.text = ''
            this.change()
        },
        selectGenre(){
            console.log(this.genreSearch)
            if (this.genreSearch == ""){
                location.reload()
            }
            else {
                axios
                    .get('http://localhost:8080/shortstories/', {params: {genre: this.genreSearch}})
                    .then((response) => {
                        console.log(response.data)
                        this.shortstories = response.data
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }
            
        },
        searchTitle() {
            this.genreSearch = ""
            if (this.titleSearch == ""){
                location.reload()
            }
            axios
                    .get('http://localhost:8080/shortstories/', {params: {title: this.titleSearch}})
                    .then((response) => {
                        console.log(response.data)
                        this.shortstories = response.data
                        if (response.data.length == 0) {
                            alert('Titel nicht gefunden.')
                            location.reload()
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                    })
        }
    }
})
