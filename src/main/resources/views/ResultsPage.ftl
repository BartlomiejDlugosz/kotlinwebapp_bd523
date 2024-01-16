<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" href="/assets/style.css">
    <title>Kotlin WebApp</title>
</head>

<body>
    <h1>Scrabble Word Score Calculator Thingy</h1>
    <form action="/submit" method="post">
        <label for="word1">Word 1:</label>
        <input type="text" name="words[word1]" id="word1">
        <label for="word2">Word 2:</label>
        <input type="text" name="words[word2]" id="word2">
        <label for="word3">Word 3:</label>
        <input type="text" name="words[word3]" id="word3">
        <label for="word4">Word 4:</label>
        <input type="text" name="words[word4]" id="word4">
        <label for="word5">Word 5:</label>
        <input type="text" name="words[word5]" id="word5">
        <input type="submit" value="Submit">
    </form>
    <ul>
        <#list results as result>
            <li>
                <#list result.charScores as charScore>
                    ${charScore.first}
                    <sub>
                        ${charScore.second}
                    </sub>
                </#list> - ${result.score}
            </li>
        </#list>
    </ul>
</body>

</html>