package starbucks.api

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory
import grails.test.mixin.TestFor
import grails.test.mongodb.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Order)
class OrderSpec extends MongoSpec {

    def setup() {
    }

    def cleanup() {
    }

    /*void "test something"() {
        expect:"fix me"
            true == false
    }*/

    @Override
	MongoClient createMongoClient() {
    MongodForTestsFactory.with(Version.Main.PRODUCTION).newMongo()
}
}
