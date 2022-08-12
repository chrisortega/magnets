/**
 * TODO(developer): Uncomment these variables before running the sample.
 */
 const topicNameOrId = 'projects/neon-mote-358900/topics/magnets';
 const data = JSON.stringify({foo: 'bar'});

// Imports the Google Cloud client library
const {PubSub} = require('@google-cloud/pubsub');

// Creates a client; cache this for further use
const pubSubClient = new PubSub();

async function publishMessageWithCustomAttributes() {
  // Publishes the message as a string, e.g. "Hello, world!" or JSON.stringify(someObject)
  const dataBuffer = Buffer.from(data);

  // Add two custom attributes, origin and username, to the message
  const customAttributes = {
    origin: 'nodejs-sample',
    username: 'gcp',
  };

  const messageId = await pubSubClient
    .topic(topicNameOrId)
    .publish(dataBuffer, customAttributes);
  console.log(`Message ${messageId} published.`);
}

publishMessageWithCustomAttributes().catch(console.error);