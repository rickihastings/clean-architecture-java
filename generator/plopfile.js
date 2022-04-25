export default function(plop) {
    plop.setHelper('strip', function(text) {
        return text.replace(/-/, '');
    });

    plop.setHelper('getEntity', function(text) {
        const parts = text.split('-');
        return parts[parts.length - 1];
    });

    plop.setHelper('getDto', function(text) {
        const parts = text.split('-');
        return `${parts[parts.length - 1]}-dto`;
    });

    // todo query

    plop.setGenerator('command', {
        description: 'Generate a command',
        prompts: [
            {
                type: 'input',
                name: 'boundary',
                message: 'command boundary (e.g. projects)'
            },
            {
                type: 'input',
                name: 'name',
                message: 'command name hyphenated (e.g. create-project, delete-project)'
            },
        ],
        actions: [
            {
                type: 'addMany',
                destination: '../src/main/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/commands/{{strip name}}',
                base: 'templates/command/main',
                templateFiles: 'templates/command/main/*.hbs'
            },
            {
                type: 'addMany',
                destination: '../src/test/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/commands/{{strip name}}',
                base: 'templates/command/test',
                templateFiles: 'templates/command/test/*.hbs'
            }
        ]
    });
};
