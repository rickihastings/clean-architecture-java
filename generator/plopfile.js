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

    plop.setHelper('getEntityFromEvent', function(text) {
        const parts = text.split('-');
        return parts[0];
    });

    plop.setGenerator('command', {
        description: 'Generate a command',
        prompts: [
            {
                type: 'input',
                name: 'boundary',
                message: 'command boundary (e.g. projects)',
            },
            {
                type: 'input',
                name: 'name',
                message: 'command name hyphenated (e.g. create-project, delete-project)',
            },
        ],
        actions: [
            {
                type: 'addMany',
                destination: '../src/main/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/commands/{{strip name}}',
                base: 'templates/command/main',
                templateFiles: 'templates/command/main/*.hbs',
            },
            {
                type: 'addMany',
                destination: '../src/test/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/commands/{{strip name}}',
                base: 'templates/command/test',
                templateFiles: 'templates/command/test/*.hbs',
            },
        ],
    });

    plop.setGenerator('query', {
        description: 'Generate a query',
        prompts: [
            {
                type: 'input',
                name: 'boundary',
                message: 'query boundary (e.g. projects)'
            },
            {
                type: 'input',
                name: 'name',
                message: 'query name hyphenated (e.g. get-project, get-projects)'
            },
        ],
        actions: [
            {
                type: 'addMany',
                destination: '../src/main/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/queries/{{strip name}}',
                base: 'templates/query/main',
                templateFiles: 'templates/query/main/*.hbs'
            },
            {
                type: 'addMany',
                destination: '../src/test/java/com/rickihastings/cleanarchitecture/application/{{boundary}}/queries/{{strip name}}',
                base: 'templates/query/test',
                templateFiles: 'templates/query/test/*.hbs'
            }
        ]
    });

    plop.setGenerator('event', {
        description: 'Generate an event',
        prompts: [
            {
                type: 'input',
                name: 'name',
                message: 'event name hyphenated (e.g. project-created, project-deleted)'
            },
        ],
        actions: [
            {
                type: 'addMany',
                destination: '../src/main/java/com/rickihastings/cleanarchitecture/application/eventhandlers',
                base: 'templates/event/eventhandlers',
                templateFiles: 'templates/event/eventhandlers/*.hbs'
            },
            {
                type: 'addMany',
                destination: '../src/main/java/com/rickihastings/cleanarchitecture/domain/events',
                base: 'templates/event/event',
                templateFiles: 'templates/event/event/*.hbs'
            }
        ]
    });
};
