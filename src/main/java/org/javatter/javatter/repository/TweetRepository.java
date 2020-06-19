package org.javatter.javatter.repository;

import org.javatter.javatter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

// classではなくinterface
// JpaRepositoryの<>の中身は１つ目にEntityクラス名、２つ目にPKの型
public interface TweetRepository extends JpaRepository<Tweet, Long> {
	
}
