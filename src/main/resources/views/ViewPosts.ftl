<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" href="/assets/style.css">
    <title>Kotlin WebApp</title>
</head>

<body>
    <h1>Scrabble Word Score Calculator Thingy</h1>
    <form action="/posts/add" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required>
        <label for="body">Body:</label>
        <input type="text" name="body" id="body" required>
        <input type="submit" value="Submit">
    </form>
    <#list posts as post>
        <div>
            <h2>
                ${post.title}
            </h2>
            <p>
                ${post.body}
            </p>
            <br>
        </div>
    </#list>
</body>

</html>